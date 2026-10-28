# MyBuilding — Plateforme de Gestion de Copropriété (PFM)

---

## 1. Description du Projet

**MyBuilding** est une application web modulaire et distribuée conçue pour la gestion administrative, technique et financière d’un immeuble en copropriété (syndic). 

Développée selon une **architecture microservices**, l’application répond aux besoins clés du syndic et des copropriétaires :
* **Gestion des résidents et du parc immobilier** : enregistrement des copropriétaires et locataires, gestion des appartements et tenue à jour du solde de chaque résident.
* **Gestion technique et maintenance** : signalement des pannes via des tickets d'incidents, planification des ordres d'intervention (WorkOrders) pour les artisans et suivi des coûts de réparation.
* **Gestion financière et comptable** : émission des appels de charges mensuels par appartement, enregistrement des règlements (partiels ou totaux), contrôle strict des impayés et imputation des dépenses de maintenance au budget.

---

## 2. Diagramme de Classes UML Global

Ce diagramme présente les entités métier de chacun des services de la plateforme, leurs énumérations, leurs relations internes ainsi que les **dépendances inter-services matérialisées par des flèches en pointillés (`..>`)**, indépendantes des protocoles de communication sous-jacents (REST/Feign, Kafka, etc.).

```mermaid
classDiagram
    direction TB

    namespace Service_Residents {
        class RoleResident {
            <<enumeration>>
            PROPRIETAIRE
            LOCATAIRE
        }
        class Resident {
            -Long id
            -String nom
            -String email
            -String telephone
            -RoleResident role
            -BigDecimal solde
        }
        class Apartment {
            -Long id
            -String numero
            -Integer etage
            -BigDecimal superficie
            -Long residentId
        }
    }

    namespace Service_Incidents {
        class PrioriteTicket {
            <<enumeration>>
            BASSE
            MOYENNE
            HAUTE
            URGENTE
        }
        class StatutTicket {
            <<enumeration>>
            OUVERT
            EN_COURS
            RESOLU
            ANNULE
        }
        class StatutWorkOrder {
            <<enumeration>>
            PLANIFIE
            EN_COURS
            CLOTURE
            ANNULE
        }
        class Ticket {
            -Long id
            -Long apartmentId
            -String description
            -PrioriteTicket priorite
            -StatutTicket statut
        }
        class WorkOrder {
            -Long id
            -Long ticketId
            -String artisan
            -LocalDate dateIntervention
            -BigDecimal cout
            -StatutWorkOrder statut
        }
    }

    namespace Service_Finances {
        class StatutCharge {
            <<enumeration>>
            EMISE
            PARTIELLE
            PAYEE
        }
        class MoyenPaiement {
            <<enumeration>>
            VIREMENT
            ESPECES
            CHEQUE
            CARTE
        }
        class Charge {
            -Long id
            -Long apartmentId
            -String periode
            -BigDecimal montant
            -BigDecimal montantPaye
            -StatutCharge statut
        }
        class Payment {
            -Long id
            -Long chargeId
            -LocalDate date
            -BigDecimal montant
            -MoyenPaiement moyen
        }
    }

    %% Relations internes au service Residents
    Apartment "0..1" --> "0..1" Resident : occupe (residentId)
    Resident --> RoleResident

    %% Relations internes au service Incidents
    Ticket --> PrioriteTicket
    Ticket --> StatutTicket
    WorkOrder --> StatutWorkOrder
    WorkOrder "0..*" --> "1" Ticket : associé à (ticketId)

    %% Relations internes au service Finances
    Charge --> StatutCharge
    Payment --> MoyenPaiement
    Payment "0..*" --> "1" Charge : règle (chargeId)

    %% Interactions entre services (dépendances inter-services en pointillés)
    Ticket ..> Apartment : dépendance (apartmentId)
    Charge ..> Apartment : dépendance (apartmentId)
    Payment ..> Resident : impacte solde (déduction dette)
    WorkOrder ..> Charge : imputation coût intervention
```

---

## 3. Schéma de l'Architecture (Les 5 Applications et leurs Ports)

L'architecture globale repose sur **cinq applications** interconnectées pour assurer l'enregistrement, la découverte et le routage dynamique :

```mermaid
flowchart TB
    Client(["Client HTTP / Postman / Navigateur"])

    subgraph Discovery ["1. Annuaire de Découverte"]
        Eureka["Eureka Server\n(eureka-server)\nPort : 8761\nhttp://localhost:8761"]
    end

    subgraph GatewayApp ["2. Point d'Entrée Unique"]
        Gateway["API Gateway\n(api-gateway)\nPort : 8080\nhttp://localhost:8080"]
    end

    subgraph MetierApps ["3, 4, 5. Les Trois Services Métier"]
        Residents["Service Residents\n(service-residents)\nPort : 8081\n• /api/residents/**\n• /api/apartments/**"]
        Incidents["Service Incidents\n(service-incidents)\nPort : 8082\n• /api/tickets/**\n• /api/work-orders/**"]
        Finances["Service Finances\n(service-finances)\nPort : 8083\n• /api/charges/**\n• /api/payments/**"]
    end

    Client -->|Requêtes API / Frontend| Gateway

    Residents -.->|S'enregistre auprès de| Eureka
    Incidents -.->|S'enregistre auprès de| Eureka
    Finances -.->|S'enregistre auprès de| Eureka

    Gateway -.->|Découverte dynamique des services| Eureka

    Gateway ==>|Routage dynamique : lb://SERVICE-RESIDENTS| Residents
    Gateway ==>|Routage dynamique : lb://SERVICE-INCIDENTS| Incidents
    Gateway ==>|Routage dynamique : lb://SERVICE-FINANCES| Finances
```

### Récapitulatif des 5 Applications :

| N° | Application | Nom Spring (`spring.application.name`) | Port | Rôle & Description |
| :-: | :--- | :--- | :---: | :--- |
| **1** | **Eureka Server** | `eureka-server` | **8761** | Annuaire centralisé d'enregistrement et de découverte des services |
| **2** | **API Gateway** | `api-gateway` | **8080** | Passerelle unique avec routage dynamique et Load Balancing |
| **3** | **Service Residents** | `service-residents` | **8081** | Gestion des résidents, des appartements et des soldes |
| **4** | **Service Incidents** | `service-incidents` | **8082** | Gestion des tickets de pannes et des ordres de travail (artisans) |
| **5** | **Service Finances** | `service-finances` | **8083** | Gestion des appels de charges, des paiements et du budget |

---

## 4. Routage Dynamique via l'API Gateway

Toutes les requêtes passent par l'API Gateway sur le port **8080**. Grâce à l'intégration avec Eureka (`lb://`), l'adresse et le port des services cibles sont résolus dynamiquement :

| Domaine Métier | Route d'accès Gateway (Port 8080) | Destination Résolue via Eureka |
| :--- | :--- | :--- |
| **Résidents** | `GET http://localhost:8080/api/residents` | `lb://SERVICE-RESIDENTS/api/residents` |
| **Appartements**| `GET http://localhost:8080/api/apartments` | `lb://SERVICE-RESIDENTS/api/apartments` |
| **Tickets** | `GET http://localhost:8080/api/tickets` | `lb://SERVICE-INCIDENTS/api/tickets` |
| **Ordres de travail** | `GET http://localhost:8080/api/work-orders` | `lb://SERVICE-INCIDENTS/api/work-orders` |
| **Charges** | `GET http://localhost:8080/api/charges` | `lb://SERVICE-FINANCES/api/charges` |
| **Paiements** | `GET http://localhost:8080/api/payments` | `lb://SERVICE-FINANCES/api/payments` |

Routage par Discovery Locator également disponible :  
`http://localhost:8080/<service-name>/...` (ex: `http://localhost:8080/service-residents/api/residents`).

---

## 5. Démarrage et Validation

### Ordre de lancement :
1. **`eureka-server`** (port 8761) — Interface web : [http://localhost:8761](http://localhost:8761)
2. **`service-residents`** (port 8081)
3. **`service-incidents`** (port 8082)
4. **`service-finances`** (port 8083)
5. **`api-gateway`** (port 8080)

Une fois tous les services démarrés, Eureka affiche les 4 clients connectés avec le statut **UP**.

---

## 6. Versionnage Git & Jalon Livré

* **Tag officiel du jalon** : `jalon-1` / `v1.0.0-jalon1`
* **Dépôt GitHub** : [https://github.com/SAADOUARRAD/MyBuilding](https://github.com/SAADOUARRAD/MyBuilding)
