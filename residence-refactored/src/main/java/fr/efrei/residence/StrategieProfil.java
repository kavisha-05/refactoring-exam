package fr.efrei.residence;

public interface StrategieProfil {
    double appliquerRemise(double montant, int echelonBourse);
    boolean estSolvable(double ressources, double loyerChargesComprises);
    double calculerCaution(double loyerHorsCharges);
    double fraisDossier();
    int dureePreavisJours();
    double calculerPenaliteRetard(double loyerChargesComprises);

    default boolean echelonValide(int echelon) { return true; }
    default boolean telephoneValide(String telephone) { return true; }
    default boolean emailSupplementaire() { return false; }
}
