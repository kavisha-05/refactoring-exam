package fr.efrei.residence;

import fr.efrei.residence.contrat.Canal;
import fr.efrei.residence.contrat.DemandeAttribution;
import fr.efrei.residence.contrat.DemandeInscription;
import fr.efrei.residence.contrat.Profil;
import fr.efrei.residence.contrat.StatutBail;
import fr.efrei.residence.contrat.TypeLogement;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsolationResidenceTest {
    private static final LocalDate DATE_SIGNATURE = LocalDate.of(2026, 9, 1);

    @Test
    void deuxResidencesConserventLeursEtudiantsBauxEtNotifications() {
        NouveauSystemeAdapter premiere = creerResidence();
        String etudiantA = inscrire(premiere, "alice@example.com", Canal.SMS);
        String bailA = attribuer(premiere, etudiantA);

        NouveauSystemeAdapter seconde = creerResidence();
        String etudiantB = inscrire(seconde, "alice@example.com", Canal.COURRIER);
        String bailB = attribuer(seconde, etudiantB);

        assertEquals("ETU-1", etudiantA);
        assertEquals("ETU-1", etudiantB);
        assertEquals("BAIL-001", bailA);
        assertEquals("BAIL-001", bailB);
        assertEquals("ETU-2", inscrire(premiere, "bilal@example.com", Canal.EMAIL));
        assertEquals("ETU-2", inscrire(seconde, "bilal@example.com", Canal.EMAIL));
        assertEquals(1, premiere.logement("A001").orElseThrow().occupants());
        assertEquals(1, seconde.logement("A001").orElseThrow().occupants());
        assertEquals(Canal.SMS, premiere.notifications().getFirst().canal());
        assertEquals(Canal.COURRIER, seconde.notifications().getFirst().canal());
        assertTrue(premiere.rapport().contains("SMS envoyés  : 1"));
        assertTrue(premiere.rapport().contains("Timbres      : 0,00"));
        assertTrue(seconde.rapport().contains("SMS envoyés  : 0"));
        assertTrue(seconde.rapport().contains("Timbres      : 1,29"));

        assertTrue(premiere.entrer(bailA, DATE_SIGNATURE));
        assertEquals(StatutBail.ACTIF, premiere.consulter(bailA).orElseThrow().statut());
        assertEquals(StatutBail.SIGNE, seconde.consulter(bailB).orElseThrow().statut());
    }

    @Test
    void reinitialiserUneResidenceNeModifiePasLAutre() {
        NouveauSystemeAdapter premiere = creerResidence();
        String bailA = attribuer(premiere, inscrire(premiere, "alice@example.com", Canal.SMS));
        NouveauSystemeAdapter seconde = creerResidence();
        String bailB = attribuer(seconde, inscrire(seconde, "emma@example.com", Canal.COURRIER));
        String rapportB = seconde.rapport();
        var notificationsB = seconde.notifications();

        premiere.reinitialiser();

        assertTrue(premiere.consulter(bailA).isEmpty());
        assertTrue(premiere.notifications().isEmpty());
        assertEquals(0, premiere.logement("A001").orElseThrow().occupants());
        assertEquals(StatutBail.SIGNE, seconde.consulter(bailB).orElseThrow().statut());
        assertEquals(rapportB, seconde.rapport());
        assertEquals(notificationsB, seconde.notifications());
        String nouvelEtudiant = inscrire(premiere, "alice@example.com", Canal.EMAIL);
        assertEquals("ETU-1", nouvelEtudiant);
        assertEquals("BAIL-001", attribuer(premiere, nouvelEtudiant));
    }

    private NouveauSystemeAdapter creerResidence() {
        NouveauSystemeAdapter systeme = new NouveauSystemeAdapter();
        systeme.reinitialiser();
        return systeme;
    }

    private String inscrire(NouveauSystemeAdapter systeme, String email, Canal canal) {
        return systeme.inscrire(new DemandeInscription("Martin", "Alice", email, "0601020304",
                "12 rue des Lilas", Profil.STANDARD, 0, 5000, false, canal)).orElseThrow();
    }

    private String attribuer(NouveauSystemeAdapter systeme, String etudiant) {
        return systeme.attribuer(new DemandeAttribution(etudiant, TypeLogement.STUDIO,
                Set.of(), DATE_SIGNATURE)).orElseThrow();
    }
}
