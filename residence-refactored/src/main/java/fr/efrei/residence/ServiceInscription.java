package fr.efrei.residence;

public class ServiceInscription implements Inscription {
    private final EtudiantRepository etudiants;
    private int compteur;

    public ServiceInscription(EtudiantRepository etudiants) { this.etudiants = etudiants; }

    public Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone, String adresse,
                                     Profil profil, int echelonBourse, double ressources, boolean pmr,
                                     Canal canalPrefere) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("Inscription refusée : nom obligatoire");
            return null;
        }
        if (!Utils.emailValide(email)) {
            System.out.println("Inscription refusée : email invalide (" + email + ")");
            return null;
        }
        if (etudiants.trouverParEmail(email) != null) {
            System.out.println("Inscription refusée : " + email + " déjà inscrit");
            return null;
        }
        if (profil == null) {
            System.out.println("Inscription refusée : profil inconnu " + profil);
            return null;
        }
        StrategieProfil strategie = StrategiesProfil.pour(profil);
        if (!strategie.echelonValide(echelonBourse)) {
            System.out.println("Inscription refusée : échelon de bourse invalide");
            return null;
        }
        if (!strategie.telephoneValide(telephone)) {
            System.out.println("Inscription refusée : numéro international (+XX) obligatoire");
            return null;
        }
        if (canalPrefere == Canal.SMS && telephone == null) {
            System.out.println("Inscription refusée : téléphone obligatoire pour les SMS");
            return null;
        }
        if (canalPrefere == Canal.COURRIER && (adresse == null || adresse.isEmpty())) {
            System.out.println("Inscription refusée : adresse obligatoire pour le courrier");
            return null;
        }
        Etudiant e = new Etudiant("ETU-" + (++compteur), nom, prenom, email, telephone, adresse, profil, echelonBourse, ressources, pmr,
                canalPrefere);
        etudiants.sauvegarder(e);
        System.out.println("Étudiant inscrit : " + e.getId() + " " + e.getNomComplet() + " (" + profil + ")");
        return e;
    }

}
