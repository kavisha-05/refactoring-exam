package fr.efrei.residence;

import java.util.HashMap;
import java.util.Map;

public class EtudiantsEnMemoire implements EtudiantRepository {
    private final Map<String, Etudiant> etudiants = new HashMap<>();

    @Override public Etudiant trouverParId(String id) {
        for (Etudiant etudiant : etudiants.values()) {
            if (etudiant.getId().equals(id)) return etudiant;
        }
        return null;
    }

    @Override public Etudiant trouverParEmail(String email) {
        System.out.println("[SQL] SELECT * FROM etudiant WHERE email = '" + email + "'");
        return etudiants.get(email);
    }

    @Override public void sauvegarder(Etudiant e) {
        System.out.println("[SQL] INSERT INTO etudiant (id, nom, email, profil) VALUES ('" + e.getId() + "', '" + e.getNom() + "', '" + e.getEmail() + "', '" + e.getProfil() + "')");
        etudiants.put(e.getEmail(), e);
    }
}
