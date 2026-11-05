package fr.efrei.residence;

import fr.efrei.residence.contrat.DemandeAttribution;
import fr.efrei.residence.contrat.DemandeDevis;
import fr.efrei.residence.contrat.DemandeInscription;
import fr.efrei.residence.contrat.DemandeLogement;
import fr.efrei.residence.contrat.EtatBail;
import fr.efrei.residence.contrat.EtatLogement;
import fr.efrei.residence.contrat.Notification;
import fr.efrei.residence.contrat.SystemeResidence;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

/**
 * Traduit les enums du contrat vers les enums internes et inversement.
 */
public class NouveauSystemeAdapter implements SystemeResidence {

    private GestionResidence gestion;
    private EtudiantRepository etudiants;
    private BailRepository baux;
    private ParcLogements parc;
    private JournalNotifications journal;
    private CalculLoyer calculateur;
    private GenerateurQuittance quittances;

    @Override
    public void reinitialiser() {
        etudiants = new EtudiantsEnMemoire();
        baux = new BauxEnMemoire();
        parc = new ParcEnMemoire();
        journal = new JournalNotificationsEnMemoire();
        CanauxNotification canaux = FabriqueNotification.avecCanauxStandards(journal);
        NotificationsResidence notifications = new ServiceNotifications(canaux);
        calculateur = new CalculateurLoyer(new AssemblageLoyer());
        Inscription inscriptions = new ServiceInscription(etudiants);
        Attribution attribution = new ServiceAttribution(parc, baux, calculateur, notifications);
        CycleBail cycle = new ServiceCycleBail(baux, notifications);
        Relances relances = new ServiceRelances(baux, canaux);
        RapportResidence rapports = new ServiceRapport(parc, baux, journal);
        quittances = new ServiceQuittance();
        gestion = new GestionResidence(inscriptions, attribution, cycle, relances, rapports);
    }

    @Override
    public Optional<String> inscrire(DemandeInscription d) {
        Etudiant e = gestion.inscrireEtudiant(d.nom(), d.prenom(), d.email(), d.telephone(), d.adresse(),
                Profil.valueOf(d.profil().name()), d.echelonBourse(), d.ressourcesMensuelles(), d.pmr(), Canal.valueOf(d.canalPrefere().name()));
        return Optional.ofNullable(e).map(Etudiant::getId);
    }

    @Override
    public boolean ajouterLogement(DemandeLogement d) {
        int param = d.type() == fr.efrei.residence.contrat.TypeLogement.COLOCATION ? d.places() : d.surfaceBalcon();
        return gestion.ajouterLogement(TypeLogement.valueOf(d.type().name()), d.numero(), d.surface(), d.etage(), d.meuble(), param) != null;
    }

    @Override
    public Optional<EtatLogement> logement(String numero) {
        Logement l = parc.trouver(numero);
        if (l == null) {
            return Optional.empty();
        }
        return Optional.of(new EtatLogement(l.getNumero(), fr.efrei.residence.contrat.TypeLogement.valueOf(l.getType().name()), l.getNombrePlaces(),
                l.getNombreOccupants(), l.estAccessiblePMR()));
    }

    @Override
    public OptionalDouble devisLoyer(DemandeDevis d) {
        Logement l = parc.trouver(d.numeroLogement());
        if (l == null) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(calculateur.calculerLoyer(l, Profil.valueOf(d.profil().name()), d.echelonBourse(),
                options(d.options())));
    }

    @Override
    public Optional<String> attribuer(DemandeAttribution d) {
        Etudiant e = etudiants.trouverParId(d.refEtudiant());
        if (e == null) {
            return Optional.empty();
        }
        Bail b = gestion.attribuerLogement(e, TypeLogement.valueOf(d.type().name()), options(d.options()), d.dateSignature());
        return Optional.ofNullable(b).map(Bail::getId);
    }

    @Override
    public Optional<EtatBail> consulter(String idBail) {
        Bail b = baux.trouverParId(idBail);
        if (b == null) {
            return Optional.empty();
        }
        return Optional.of(new EtatBail(b.getId(), b.getLogement().getNumero(), fr.efrei.residence.contrat.StatutBail.valueOf(b.getStatut().name()),
                b.getLoyerHorsCharges(), b.getCharges(), b.getLoyerChargesComprises(), b.getCaution(),
                b.getFraisDossier(), b.getDateFin(), b.soldeDu, b.cautionRestituee));
    }

    @Override
    public boolean entrer(String idBail, LocalDate date) {
        return gestion.entrerDansLesLieux(idBail, date);
    }

    @Override
    public boolean payer(String idBail, YearMonth mois, LocalDate date, double montant) {
        return gestion.payerLoyer(idBail, mois.toString(), date, montant);
    }

    @Override
    public boolean donnerPreavis(String idBail, LocalDate date) {
        return gestion.donnerPreavis(idBail, date);
    }

    @Override
    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return gestion.sortir(idBail, date, retenuesDegats);
    }

    @Override
    public boolean annuler(String idBail) {
        return gestion.annulerBail(idBail);
    }

    @Override
    public Optional<String> quittance(String idBail, YearMonth mois) {
        return Optional.ofNullable(baux.trouverParId(idBail)).map(b -> quittances.generer(b, mois.toString()));
    }

    @Override
    public void relancerImpayes() {
        gestion.relancerImpayes();
    }

    @Override
    public String rapport() {
        return gestion.genererRapport();
    }

    @Override
    public List<Notification> notifications() {
        return journal.consulter().stream()
                .map(notification -> new Notification(
                        fr.efrei.residence.contrat.Canal.valueOf(notification.canal().name()),
                        notification.destinataire(), notification.contenu()))
                .toList();
    }

    private static Set<Option> options(Set<fr.efrei.residence.contrat.Option> optionsContrat) {
        Set<Option> options = EnumSet.noneOf(Option.class);
        for (fr.efrei.residence.contrat.Option option : optionsContrat) {
            options.add(Option.valueOf(option.name()));
        }
        return options;
    }
}
