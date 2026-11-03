package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssemblageLoyerTest {
    private static final double PRECISION = 1e-9;
    private final AssemblageLoyer assemblage = new AssemblageLoyer();
    private final CalculateurLoyer calculateur = new CalculateurLoyer(new AssemblageLoyer());

    @Test
    void sansOptionNiMajorationConserveLeLoyerDeBase() {
        Logement logement = FabriqueLogement.creer(TypeLogement.STUDIO, "TEST", 26, 1, false, 0);

        assertEquals(364, assemblage.assembler(logement, Set.of()).calculer(), PRECISION);
    }

    @Test
    void appliqueLeMenageAvantLesOptionsFixesQuelQueSoitLeurOrdreDansLeSet() {
        Logement logement = FabriqueLogement.creer(TypeLogement.STUDIO, "TEST", 26, 0, true, 0);
        Set<Option> ordreInverse = new LinkedHashSet<>(List.of(
                Option.ASSURANCE, Option.FIBRE, Option.LAVERIE, Option.PARKING, Option.MENAGE));

        assertEquals(515.4836, assemblage.assembler(logement, EnumSet.allOf(Option.class)).calculer(), PRECISION);
        assertEquals(515.4836, assemblage.assembler(logement, ordreInverse).calculer(), PRECISION);
    }

    @Test
    void appliqueLaRemiseSurToutesLesCouchesEtConserveLesCharges() {
        Logement logement = FabriqueLogement.creer(TypeLogement.STUDIO, "TEST", 26, 0, true, 0);

        assertEquals(463.94, calculateur.calculerLoyer(logement, Profil.ALTERNANT, 0,
                EnumSet.allOf(Option.class)), PRECISION);
        assertEquals(35, calculateur.calculerCharges(logement), PRECISION);
    }

    @Test
    void utiliseLeMenageReduitEtLaFibreIncluseEnColocation() {
        Logement logement = FabriqueLogement.creer(TypeLogement.COLOCATION, "TEST", 80, 0, true, 3);
        Set<Option> sansFibre = EnumSet.allOf(Option.class);
        sansFibre.remove(Option.FIBRE);

        assertEquals(399.2853333333333,
                assemblage.assembler(logement, EnumSet.allOf(Option.class)).calculer(), PRECISION);
        assertEquals(399.2853333333333, assemblage.assembler(logement, sansFibre).calculer(), PRECISION);
        assertEquals(25, calculateur.calculerCharges(logement), PRECISION);
    }

    @Test
    void arronditSeulementApresLesOptionsEtLaRemisePlafonnee() {
        Logement logement = FabriqueLogement.creer(TypeLogement.COLOCATION, "TEST", 80, 1, false, 3);
        Set<Option> options = EnumSet.allOf(Option.class);

        assertEquals(371.0666666666667, assemblage.assembler(logement, options).calculer(), PRECISION);
        assertEquals(259.75, calculateur.calculerLoyer(logement, Profil.BOURSIER, 7, options), PRECISION);
    }
}
