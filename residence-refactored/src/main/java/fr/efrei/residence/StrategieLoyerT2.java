package fr.efrei.residence;

public class StrategieLoyerT2 implements StrategieLoyer {
    private static final double PRIX_M2 = 12;
    private static final double FORFAIT = 100;

    @Override
    public double calculerBase(Logement logement) {
        return logement.getSurface() * PRIX_M2 + FORFAIT;
    }
}
