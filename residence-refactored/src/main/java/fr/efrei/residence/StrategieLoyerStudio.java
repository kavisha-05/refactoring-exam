package fr.efrei.residence;

public class StrategieLoyerStudio implements StrategieLoyer {
    private static final double PRIX_M2 = 14;
    private static final double LOYER_MINIMUM = 350;

    @Override
    public double calculerBase(Logement logement) {
        return Math.max(logement.getSurface() * PRIX_M2, LOYER_MINIMUM);
    }
}
