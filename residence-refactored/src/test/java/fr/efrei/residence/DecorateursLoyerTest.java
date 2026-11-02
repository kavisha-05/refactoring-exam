package fr.efrei.residence;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateursLoyerTest {
    private static final double PRECISION = 1e-9;

    @ParameterizedTest(name = "{0}")
    @MethodSource("decorateurs")
    void appliqueLaCoucheSansArrondir(String nom, UnaryOperator<Loyer> decorateur, double attendu) {
        Loyer base = () -> 100.125;

        assertEquals(attendu, decorateur.apply(base).calculer(), PRECISION);
        assertEquals(100.125, base.calculer(), PRECISION);
    }

    static Stream<Arguments> decorateurs() {
        return Stream.of(
                Arguments.of("Meuble", (UnaryOperator<Loyer>) LoyerMeuble::new, 115.14375),
                Arguments.of("Rez-de-chaussee", (UnaryOperator<Loyer>) LoyerRezDeChaussee::new, 95.11875),
                Arguments.of("Menage individuel", (UnaryOperator<Loyer>) loyer -> new LoyerMenage(loyer, 1.08), 108.135),
                Arguments.of("Menage colocation", (UnaryOperator<Loyer>) loyer -> new LoyerMenage(loyer, 1.04), 104.13),
                Arguments.of("Parking", (UnaryOperator<Loyer>) LoyerParking::new, 145.125),
                Arguments.of("Laverie", (UnaryOperator<Loyer>) LoyerLaverie::new, 112.125),
                Arguments.of("Fibre individuelle", (UnaryOperator<Loyer>) loyer -> new LoyerFibre(loyer, 20), 120.125),
                Arguments.of("Fibre colocation", (UnaryOperator<Loyer>) loyer -> new LoyerFibre(loyer, 0), 100.125),
                Arguments.of("Assurance", (UnaryOperator<Loyer>) LoyerAssurance::new, 109.125)
        );
    }
}
