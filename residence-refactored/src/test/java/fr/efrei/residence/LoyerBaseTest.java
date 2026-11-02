package fr.efrei.residence;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoyerBaseTest {
    private static final double PRECISION = 1e-9;

    @ParameterizedTest
    @CsvSource({
            "STUDIO, 20, 0, 350",
            "STUDIO, 25, 0, 350",
            "STUDIO, 25.01, 0, 350.14",
            "T1, 28, 0, 414",
            "T2, 45, 6, 640",
            "COLOCATION, 80, 3, 293.3333333333333"
    })
    void calculeUniquementLaBaseSansArrondi(TypeLogement type, double surface, int param, double attendu) {
        Logement logement = FabriqueLogement.creer(type, "TEST", surface, 0, true, param);
        Loyer base = new LoyerBase(logement, StrategiesLoyer.pour(type));

        assertEquals(attendu, base.calculer(), PRECISION);
    }
}
