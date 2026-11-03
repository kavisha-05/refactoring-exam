package fr.efrei.residence;

public interface EtudiantRepository {
    Etudiant trouverParEmail(String email);
    Etudiant trouverParId(String id);
    void sauvegarder(Etudiant etudiant);
}
