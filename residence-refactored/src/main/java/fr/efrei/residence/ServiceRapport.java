package fr.efrei.residence;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ServiceRapport implements RapportResidence {
    private static final double POURCENTAGE = 100.0;
    private final ParcLogements parc;
    private final BailRepository baux;
    private final JournalNotifications journal;

    public ServiceRapport(ParcLogements parc, BailRepository baux, JournalNotifications journal) {
        this.parc = parc;
        this.baux = baux;
        this.journal = journal;
    }

    @Override
    public String genererRapport() {
        String r = "";
        r += "=============== RAPPORT " + Residence.NOM + " ===============" + "\n";
        Map<TypeLogement, Integer> capacites = new EnumMap<>(TypeLogement.class);
        Map<TypeLogement, Integer> occupations = new EnumMap<>(TypeLogement.class);
        double surfaceBalcons = 0;
        for (Logement l : parc.tous()) {
            capacites.merge(l.getType(), l.getNombrePlaces(), Integer::sum);
            occupations.merge(l.getType(), l.getNombreOccupants(), Integer::sum);
            try {
                surfaceBalcons += l.getSurfaceBalcon();
            } catch (UnsupportedOperationException ex) {
            }
        }
        r += "Studios      : " + occupations.getOrDefault(TypeLogement.STUDIO, 0) + "/" + capacites.getOrDefault(TypeLogement.STUDIO, 0) + "\n";
        r += "T1           : " + occupations.getOrDefault(TypeLogement.T1, 0) + "/" + capacites.getOrDefault(TypeLogement.T1, 0) + "\n";
        r += "T2           : " + occupations.getOrDefault(TypeLogement.T2, 0) + "/" + capacites.getOrDefault(TypeLogement.T2, 0) + "\n";
        r += "Colocation   : " + occupations.getOrDefault(TypeLogement.COLOCATION, 0) + "/" + capacites.getOrDefault(TypeLogement.COLOCATION, 0) + " places" + "\n";
        int total = capacites.values().stream().mapToInt(Integer::intValue).sum();
        int occupes = occupations.values().stream().mapToInt(Integer::intValue).sum();
        r += "Occupation   : " + Math.round(occupes * POURCENTAGE / total) + " %" + "\n";
        r += "Balcons      : " + surfaceBalcons + " m²" + "\n";

        double loyersMensuels = 0;
        double impayes = 0;
        double cautions = 0;
        List<Bail> listeBaux = baux.tous();
        for (Bail b : listeBaux) {
            if (b.getStatut() == StatutBail.ACTIF || b.getStatut() == StatutBail.PREAVIS) {
                loyersMensuels += b.getLoyerChargesComprises();
                cautions += b.getCaution();
            }
            if (b.soldeDu > 0) impayes += b.soldeDu;
        }
        r += "Loyers CC    : " + Utils.euros(Utils.arrondir(loyersMensuels)) + " / mois" + "\n";
        r += "Cautions     : " + Utils.euros(Utils.arrondir(cautions)) + "\n";
        r += "Impayés      : " + Utils.euros(Utils.arrondir(impayes)) + "\n";
        r += "SMS envoyés  : " + journal.nombreSms() + "\n";
        r += "Timbres      : " + Utils.euros(Utils.arrondir(journal.coutTimbres())) + "\n";
        r += "============================================================";
        return r;
    }

}
