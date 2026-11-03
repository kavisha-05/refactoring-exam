package fr.efrei.residence;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServicesResidenceTest {
    @Test
    void inscriptionAttributionEtCycleUtilisentLesMemesDependances() {
        EtudiantRepository etudiants = new EtudiantsEnMemoire();
        BailRepository baux = new BauxEnMemoire();
        ParcLogements parc = new ParcEnMemoire();
        NotificationsResidence notifications = (etudiant, sujet, message) -> { };
        Inscription inscription = new ServiceInscription(etudiants);
        Attribution attribution = new ServiceAttribution(parc, baux, new CalculateurLoyer(new AssemblageLoyer()), notifications);
        CycleBail cycle = new ServiceCycleBail(baux, notifications);

        Etudiant etudiant = inscription.inscrireEtudiant("Martin", "Alice", "alice@example.com", "0601020304", null,
                Profil.STANDARD, 0, 5000, false, Canal.EMAIL);
        assertNotNull(etudiant);
        assertEquals("ETU-1", etudiant.getId());
        Bail bail = attribution.attribuerLogement(etudiant, TypeLogement.STUDIO, Set.of(), LocalDate.of(2026, 9, 1));
        assertNotNull(bail);
        assertEquals("BAIL-001", bail.getId());
        assertTrue(cycle.entrerDansLesLieux(bail.getId(), LocalDate.of(2026, 9, 1)));
        assertTrue(cycle.payerLoyer(bail.getId(), "2026-09", LocalDate.of(2026, 9, 5), bail.getLoyerChargesComprises()));
        assertTrue(cycle.donnerPreavis(bail.getId(), LocalDate.of(2026, 10, 1)));
        assertEquals(StatutBail.PREAVIS, baux.trouverParId(bail.getId()).getStatut());
    }
}
