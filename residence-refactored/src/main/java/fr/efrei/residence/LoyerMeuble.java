package fr.efrei.residence;

public class LoyerMeuble extends DecorateurLoyer {
    private static final double COEFFICIENT = 1.15;

    public LoyerMeuble(Loyer loyer) { super(loyer); }

    @Override public double calculer() { return loyer.calculer() * COEFFICIENT; }
}
