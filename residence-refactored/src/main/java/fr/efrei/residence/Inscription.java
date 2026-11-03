package fr.efrei.residence;

public interface Inscription {
    Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone, String adresse,
                             Profil profil, int echelonBourse, double ressources, boolean pmr, Canal canalPrefere);
}
