package fr.efrei.residence;

public class StrategieLoyerColocation implements StrategieLoyer {
    private static final double PRIX_M2 = 11;
    private static final double CHARGES = 25;
    private static final double COEFFICIENT_MENAGE = 1.04;

    @Override
    public double calculerBase(Logement logement) {
        return logement.getSurface() * PRIX_M2 / logement.getNombrePlaces();
    }

    @Override public double charges() { return CHARGES; }

    @Override public double coefficientMenage() { return COEFFICIENT_MENAGE; }

    @Override public double prixFibre() { return 0; }
}
