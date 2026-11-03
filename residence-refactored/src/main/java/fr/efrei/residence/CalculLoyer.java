package fr.efrei.residence;

import java.util.Set;

public interface CalculLoyer {
    double calculerLoyer(Logement logement, Profil profil, int echelon, Set<Option> options);
    double calculerCharges(Logement logement);
}
