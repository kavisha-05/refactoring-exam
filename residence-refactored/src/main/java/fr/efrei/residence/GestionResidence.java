package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

public class GestionResidence implements Inscription, Attribution, CycleBail, Relances, RapportResidence {
    private final Inscription inscriptions;
    private final Attribution attribution;
    private final CycleBail cycle;
    private final Relances relances;
    private final RapportResidence rapports;

    public GestionResidence(Inscription inscriptions, Attribution attribution, CycleBail cycle,
                            Relances relances, RapportResidence rapports) {
        this.inscriptions = inscriptions;
        this.attribution = attribution;
        this.cycle = cycle;
        this.relances = relances;
        this.rapports = rapports;
    }

    @Override
    public Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone, String adresse,
                             Profil profil, int echelonBourse, double ressources, boolean pmr, Canal canalPrefere) {
        return inscriptions.inscrireEtudiant(nom, prenom, email, telephone, adresse, profil, echelonBourse, ressources, pmr, canalPrefere);
    }

    @Override
    public Logement ajouterLogement(String codeType, String numero, double surface, int etage, boolean meuble, int param) {
        return attribution.ajouterLogement(codeType, numero, surface, etage, meuble, param);
    }

    @Override
    public Logement ajouterLogement(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        return attribution.ajouterLogement(type, numero, surface, etage, meuble, param);
    }

    @Override
    public Bail attribuerLogement(Etudiant etudiant, TypeLogement type, Set<Option> options, LocalDate dateSignature) {
        return attribution.attribuerLogement(etudiant, type, options, dateSignature);
    }

    @Override
    public boolean entrerDansLesLieux(String idBail, LocalDate date) {
        return cycle.entrerDansLesLieux(idBail, date);
    }

    @Override
    public boolean payerLoyer(String idBail, String mois, LocalDate datePaiement, double montant) {
        return cycle.payerLoyer(idBail, mois, datePaiement, montant);
    }

    @Override
    public boolean donnerPreavis(String idBail, LocalDate date) {
        return cycle.donnerPreavis(idBail, date);
    }

    @Override
    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return cycle.sortir(idBail, date, retenuesDegats);
    }

    @Override
    public boolean annulerBail(String idBail) {
        return cycle.annulerBail(idBail);
    }

    @Override
    public void relancerImpayes() {
        relances.relancerImpayes();
    }

    @Override
    public String genererRapport() {
        return rapports.genererRapport();
    }

    public void afficherRapport() {
        System.out.println();
        System.out.println(genererRapport());
    }
}
