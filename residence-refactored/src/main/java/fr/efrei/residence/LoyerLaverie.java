package fr.efrei.residence;

public class LoyerLaverie extends DecorateurLoyer {
    private static final double PRIX = 12;

    public LoyerLaverie(Loyer loyer) { super(loyer); }

    @Override public double calculer() { return loyer.calculer() + PRIX; }
}
