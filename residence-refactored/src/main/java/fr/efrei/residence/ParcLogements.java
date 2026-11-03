package fr.efrei.residence;

import java.util.List;

public interface ParcLogements {
    Logement trouver(String numero);
    void ajouter(Logement logement);
    List<Logement> tous();
}
