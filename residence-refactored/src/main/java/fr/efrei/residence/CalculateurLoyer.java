package fr.efrei.residence;

import java.util.Set;

public class CalculateurLoyer implements CalculLoyer {

    private final AssemblageLoyer assemblage;

    public CalculateurLoyer(AssemblageLoyer assemblage) {
        this.assemblage = assemblage;
    }

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, Set<Option> options) {
        Loyer couches = assemblage.assembler(l, options);
        double loyer = StrategiesProfil.pour(profil).appliquerRemise(couches.calculer(), echelon);
        return Utils.arrondir(loyer);
    }

    public double calculerCharges(Logement l) {
        return StrategiesLoyer.pour(l.getType()).charges();
    }
}
