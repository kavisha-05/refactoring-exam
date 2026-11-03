package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

public interface Attribution {
    Logement ajouterLogement(String codeType, String numero, double surface, int etage, boolean meuble, int param);
    Logement ajouterLogement(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param);
    Bail attribuerLogement(Etudiant etudiant, TypeLogement type, Set<Option> options, LocalDate dateSignature);
}
