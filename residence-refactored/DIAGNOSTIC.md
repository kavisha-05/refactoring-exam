# Diagnostic SOLID

| Défaut | Emplacement | Principe SOLID concerné |
| --- | --- | --- |
| La classe regroupe l'inscription, l'attribution, la gestion des baux et le rapport. | `GestionResidence` | S — responsabilité unique |
| La création des types de logements est répétée : ajouter un type impose de modifier plusieurs endroits. | `GestionResidence.ajouterLogement` et `Residence.initialiser` | O — ouvert à l'extension, fermé à la modification |
| Une colocation hérite de `Logement`, mais sa méthode `loger()` lève une exception au lieu de loger l'étudiant. | `Colocation.loger` | L — substitution de Liskov |
| L'interface impose des méthodes de colocation et de balcon à tous les logements, même aux studios qui ne les prennent pas en charge. | `ILogement` et `Studio` | I — ségrégation des interfaces |
| La gestion dépend directement de la classe concrète `Database` et récupère elle-même son instance. | `GestionResidence` et `Database.getInstance` | D — inversion des dépendances |
