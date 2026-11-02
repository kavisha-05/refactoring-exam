package fr.efrei.residence;

public class StrategieLoyerT1 implements StrategieLoyer {
    private static final double PRIX_M2 = 13;
    private static final double FORFAIT = 50;

    @Override
    public double calculerBase(Logement logement) {
        return logement.getSurface() * PRIX_M2 + FORFAIT;
    }
}
