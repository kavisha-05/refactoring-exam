package fr.efrei.residence;

public class LoyerMenage extends DecorateurLoyer {
    private final double coefficient;

    public LoyerMenage(Loyer loyer, double coefficient) {
        super(loyer);
        this.coefficient = coefficient;
    }

    @Override public double calculer() { return loyer.calculer() * coefficient; }
}
