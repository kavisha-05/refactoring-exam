# Diagnostic SOLID

## S — responsabilité unique

| | |
| --- | --- |
| **Défaut** | La classe regroupe l'inscription, l'attribution, la gestion des baux et le rapport. |
| **Emplacement** | `GestionResidence` |

## O — ouvert à l'extension, fermé à la modification

| | |
| --- | --- |
| **Défaut** | La création des types de logements est répétée : ajouter un type impose de modifier plusieurs endroits. |
| **Emplacement** | `GestionResidence.ajouterLogement` et `Residence.initialiser` |

## L — substitution de Liskov

| | |
| --- | --- |
| **Défaut** | Une colocation hérite de `Logement`, mais sa méthode `loger()` lève une exception au lieu de loger l'étudiant. |
| **Emplacement** | `Colocation.loger` |

## I — ségrégation des interfaces

| | |
| --- | --- |
| **Défaut** | L'interface impose des méthodes de colocation et de balcon à tous les logements, même aux studios qui ne les prennent pas en charge. |
| **Emplacement** | `ILogement` et `Studio` |

## D — inversion des dépendances

| | |
| --- | --- |
| **Défaut** | La gestion dépend directement de la classe concrète `Database` et récupère elle-même son instance. |
| **Emplacement** | `GestionResidence` et `Database.getInstance` |
