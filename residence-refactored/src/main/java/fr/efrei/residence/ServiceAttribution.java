package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

public class ServiceAttribution implements Attribution {
    private final ParcLogements parc;
    private final BailRepository baux;
    private final CalculLoyer calculateur;
    private final NotificationsResidence notifications;
    private int compteurBail;

    public ServiceAttribution(ParcLogements parc, BailRepository baux, CalculLoyer calculateur, NotificationsResidence notifications) {
        this.parc = parc;
        this.baux = baux;
        this.calculateur = calculateur;
        this.notifications = notifications;
    }

    public Logement ajouterLogement(String codeType, String numero, double surface, int etage, boolean meuble, int param) {
        if (parc.trouver(numero) != null) {
            System.out.println("Logement " + numero + " existe déjà");
            return null;
        }
        TypeLogement type;
        try {
            type = TypeLogement.valueOf(codeType);
        } catch (IllegalArgumentException ex) {
            System.out.println("Type de logement inconnu : " + codeType);
            return null;
        }
        return ajouterLogement(type, numero, surface, etage, meuble, param);
    }

    public Logement ajouterLogement(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        if (parc.trouver(numero) != null) {
            System.out.println("Logement " + numero + " existe déjà");
            return null;
        }
        Logement l = FabriqueLogement.creerPourAjout(type, numero, surface, etage, meuble, param);
        if (l == null) {
            System.out.println("Type de logement inconnu : " + type);
            return null;
        }
        parc.ajouter(l);
        System.out.println("Logement ajouté : " + numero + " (" + type + ")");
        return l;
    }

    // ------------------------------------------------------------------ attribution

    public Bail attribuerLogement(Etudiant e, TypeLogement typeSouhaite, Set<Option> options, LocalDate dateSignature) {
        System.out.println("--- Attribution d'un " + typeSouhaite + " à " + e.getNomComplet());
        Logement choisi = null;
        double loyer = 0;
        double charges = 0;
        for (Logement l : parc.tous().stream().filter(logement -> logement.getType() == typeSouhaite).toList()) {
            if (!l.estLibre()) continue;
            if (e.isPmr() && !l.estAccessiblePMR()) continue;

            double loyerCandidat = calculateur.calculerLoyer(l, e.getProfil(), e.getEchelonBourse(), options);
            double chargesCandidat = calculateur.calculerCharges(l);
            double total = loyerCandidat + chargesCandidat;

            // solvabilité
            boolean solvable = StrategiesProfil.pour(e.getProfil()).estSolvable(e.getRessourcesMensuelles(), total);
            if (!solvable) {
                System.out.println("  " + l.getNumero() + " : ressources insuffisantes (" + Utils.euros(total) + "/mois)");
                continue;
            }
            choisi = l;
            loyer = loyerCandidat;
            charges = chargesCandidat;
            break;
        }
        if (choisi == null) {
            System.out.println("  Aucun logement disponible pour " + e.getNomComplet());
            return null;
        }

        if (choisi instanceof Colocation) {
            choisi.ajouterColocataire(e);
        } else {
            choisi.loger(e);
        }

        compteurBail++;
        String id = "BAIL-" + String.format("%03d", compteurBail);
        Bail b = new Bail(id, e, choisi, dateSignature);
        b.setOptions(options);
        b.setLoyerHorsCharges(loyer);
        b.setCharges(charges);

        // dépôt de garantie
        StrategieProfil strategie = StrategiesProfil.pour(e.getProfil());
        double caution = strategie.calculerCaution(loyer);
        b.setCaution(Utils.arrondir(caution));

        // frais de dossier
        b.setFraisDossier(strategie.fraisDossier());

        baux.sauvegarder(b);
        System.out.println("  Bail " + id + " signé : " + choisi.getNumero() + ", " + Utils.euros(b.getLoyerChargesComprises())
                + " CC/mois, caution " + Utils.euros(b.getCaution()) + ", frais de dossier " + Utils.euros(b.getFraisDossier()));
        notifications.notifier(e, "Votre bail " + id,
                "Bienvenue à la " + Residence.NOM + " ! Logement " + choisi.getNumero() + ", loyer "
                        + Utils.euros(b.getLoyerChargesComprises()) + " charges comprises.");
        return b;
    }

}
