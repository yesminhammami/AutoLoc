# AutoLoc


## Présentation

AutoLoc est une application de gestion d'une agence de location de véhicules.

L'application permet de gérer les véhicules, les clients, les réservations, les contrats, les paiements, les employés et les opérations de maintenance.

## Acteurs

### Client

Le client peut notamment :

* consulter les véhicules disponibles ;
* consulter les informations des véhicules ;
* effectuer une réservation ;
* consulter ses réservations ;
* consulter ses contrats ;
* effectuer et consulter ses paiements.

### Agent d'agence

L'agent d'agence peut notamment :

* gérer les clients ;
* gérer les véhicules ;
* gérer les réservations ;
* gérer les contrats ;
* gérer les paiements.

### Responsable d'agence

Le responsable d'agence peut notamment :

* consulter l'activité de l'agence ;
* gérer les employés ;
* gérer les véhicules ;
* gérer les réservations ;
* suivre les statistiques ;
* gérer les opérations de maintenance.

### Administrateur

L'administrateur peut notamment :

* gérer les agences ;
* gérer les utilisateurs et les employés ;
* consulter les statistiques globales ;
* administrer la plateforme.

## Technologies

* Java 17+
* Spring Boot
* Spring Data JPA
* Spring MVC
* Spring AOP
* Spring Scheduler
* Maven
* MySQL
* Lombok
* Git / GitHub

## Architecture

Le projet adopte une architecture en couches :

* **Presentation / API** : contrôleurs REST
* **Service** : logique métier
* **Repository** : accès aux données
* **Domain** : entités et énumérations
* **DTO** : objets de transfert
* **Transverse** : aspects et tâches planifiées

## Équipe

Projet réalisé dans le cadre du TP ASI 2026-2027.
