package fr.efrei.residence;

public class StrategieStandard implements StrategieProfil {
    private static final int MULTIPLICATEUR_SOLVABILITE = 3;
    private static final double FRAIS_DOSSIER = 100;
    private static final int PREAVIS_JOURS = 30;
    private static final double TAUX_PENALITE = 0.05;
    private static final double PENALITE_MINIMUM = 10;

    public double appliquerRemise(double montant, int echelonBourse) { return montant; }
    public boolean estSolvable(double ressources, double loyerChargesComprises) {
        return ressources >= loyerChargesComprises * MULTIPLICATEUR_SOLVABILITE;
    }
    public double calculerCaution(double loyerHorsCharges) { return loyerHorsCharges; }
    public double fraisDossier() { return FRAIS_DOSSIER; }
    public int dureePreavisJours() { return PREAVIS_JOURS; }
    public double calculerPenaliteRetard(double loyerChargesComprises) {
        return Utils.arrondir(Math.max(loyerChargesComprises * TAUX_PENALITE, PENALITE_MINIMUM));
    }
}
