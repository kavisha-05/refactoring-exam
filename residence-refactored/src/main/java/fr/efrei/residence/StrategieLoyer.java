package fr.efrei.residence;

public interface StrategieLoyer {
    double CHARGES_STANDARD = 35;
    double COEFFICIENT_MENAGE_STANDARD = 1.08;
    double PRIX_FIBRE_STANDARD = 20;

    double calculerBase(Logement logement);

    default double charges() { return CHARGES_STANDARD; }

    default double coefficientMenage() { return COEFFICIENT_MENAGE_STANDARD; }

    default double prixFibre() { return PRIX_FIBRE_STANDARD; }
}
