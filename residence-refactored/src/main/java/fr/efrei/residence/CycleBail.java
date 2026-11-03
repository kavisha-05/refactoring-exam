package fr.efrei.residence;

import java.time.LocalDate;

public interface CycleBail {
    boolean entrerDansLesLieux(String idBail, LocalDate date);
    boolean payerLoyer(String idBail, String mois, LocalDate datePaiement, double montant);
    boolean donnerPreavis(String idBail, LocalDate date);
    boolean sortir(String idBail, LocalDate date, double retenuesDegats);
    boolean annulerBail(String idBail);
}
