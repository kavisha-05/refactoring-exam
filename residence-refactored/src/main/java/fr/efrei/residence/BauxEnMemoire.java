package fr.efrei.residence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BauxEnMemoire implements BailRepository {
    private final Map<String, Bail> baux = new HashMap<>();

    @Override public void sauvegarder(Bail b) {
        System.out.println("[SQL] INSERT INTO bail (id, etudiant, logement, statut, loyer) VALUES ('" + b.getId() + "', '" + b.getEtudiant().getId() + "', '" + b.getLogement().getNumero() + "', '" + b.getStatut() + "', " + b.getLoyerChargesComprises() + ")");
        baux.put(b.getId(), b);
    }

    @Override public void mettreAJour(Bail b) {
        System.out.println("[SQL] UPDATE bail SET statut = '" + b.getStatut() + "', solde = " + b.soldeDu + " WHERE id = '" + b.getId() + "'");
        baux.put(b.getId(), b);
    }

    @Override public Bail trouverParId(String id) {
        System.out.println("[SQL] SELECT * FROM bail WHERE id = '" + id + "'");
        return baux.get(id);
    }

    @Override public List<Bail> tous() {
        System.out.println("[SQL] SELECT * FROM bail");
        List<Bail> resultat = new ArrayList<>(baux.values());
        resultat.sort(Comparator.comparing(Bail::getId));
        return resultat;
    }
}
