package fr.efrei.residence;

public class LoyerAssurance extends DecorateurLoyer {
    private static final double PRIX = 9;

    public LoyerAssurance(Loyer loyer) { super(loyer); }

    @Override public double calculer() { return loyer.calculer() + PRIX; }
}
