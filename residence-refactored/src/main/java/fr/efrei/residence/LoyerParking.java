package fr.efrei.residence;

public class LoyerParking extends DecorateurLoyer {
    private static final double PRIX = 45;

    public LoyerParking(Loyer loyer) { super(loyer); }

    @Override public double calculer() { return loyer.calculer() + PRIX; }
}
