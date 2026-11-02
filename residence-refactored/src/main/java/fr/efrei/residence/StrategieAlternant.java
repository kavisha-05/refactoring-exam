package fr.efrei.residence;

public class StrategieAlternant extends StrategieStandard {
    private static final double COEFFICIENT_REMISE = 0.90;
    private static final int MULTIPLICATEUR_SOLVABILITE = 2;
    private static final int PREAVIS_JOURS = 15;

    @Override public double appliquerRemise(double montant, int echelonBourse) { return montant * COEFFICIENT_REMISE; }
    @Override public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return ressources >= loyerChargesComprises * MULTIPLICATEUR_SOLVABILITE;
    }
    @Override public int dureePreavisJours() { return PREAVIS_JOURS; }
}
