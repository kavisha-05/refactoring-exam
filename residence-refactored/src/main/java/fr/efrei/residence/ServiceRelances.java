package fr.efrei.residence;

public class ServiceRelances implements Relances {
    private final BailRepository baux;
    private final CanauxNotification canaux;

    public ServiceRelances(BailRepository baux, CanauxNotification canaux) {
        this.baux = baux;
        this.canaux = canaux;
    }

    @Override
    public void relancerImpayes() {
        System.out.println("--- Relance des impayés");
        for (Bail bail : baux.tous()) {
            if (bail.soldeDu <= 0 || bail.getStatut() == StatutBail.ANNULE) continue;
            canaux.creer(bail.getEtudiant().getCanalPrefere()).relancer(bail);
        }
    }
}
