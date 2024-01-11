# CODING WEEK 2024

---
## Points importants 


- Une app fonctionnelle
- Un système de compte utilisateur
- Un système de connexion
- Un système de chat
- Réclamation à un compte administrateur
- Planing des emprunts
- Système de géolocalisation
- Système de prêts
- Système de réservation
- Système de paiement/ récompense
- Système de recherche (par catégorie, par nom)
- Système de notation

---
## Apps similaires

- Leboncoin
- Geev
- Vented

## Technologies utilisées

- SQLite
- FormsFX
- ValidatorFX

---

## Membres du groupe

- Fondrat Julie
- Fornoff Léo
- Neyens Elise

---

---
## Roadmap 08/01/2024

### Avancement par jour

#### Premier jour
- Création du repo
- Création du README.md
- Choix du design de l'UI
- Création de la base de données
- Création des premières pages de l'app (static)
- Création des premières classes de données

#### Deuxième jour

- Création des pages de connexion, d'inscription et de profil
- Création des premières méthodes de la DAO
- Amélioration de la base de données
- Front de la messagerie
- Début de la création des formulaires de produits et services

#### Troisième jour

- Création des formulaires d'ajout de produits et de services
- Mise en service de la messagerie
- Affichage des produits et services sur la page 
- Ajout de photo pour les produits et services
- Création des formulaires de notation
- Mise en service de la barre de recherche
- Mise en service de la géolocalisation

#### Quatrième jour

- Mise en service du système de réservation
- Mise en place des calendriers
- Gestion de la partie administrateur du site
- Débugage de l'app

#### Cinquième jour

- Jour tampon pour finir les tâches non terminées
- Finition de l'app
- Vidéo de présentation

---

---

## DAY 1 (Lundi 08 janvier 2024)

### Objectifs de la journée

- Avoir une app qui tourne avec une page d'accueil
- Avoir une première version de base de données
- Avoir une idée des données à stocker dans la base de données
- Crée le lien entre l'app et la base de données
- Avoir les premières méthodes de la DAO 

### Tâches réalisées

- L'app tourne avec intellij mais pas avec gradle (problème avec les images)
- On a une page d'accueil, une barre de navigation et une page de connexion (front)
- On a une base de données avec les premières tables
- Il est possible d'ajouter un utilisateur à la base de données depuis l'app (back)
- La DAO est commencé notamment pour les utilisateurs

### Difficultés rencontrées

- Problèmes lors de merges avec des fichiers qui ont été supprimés sans que l'on s'en rende compte tout de suite
- Problèmes avec les images qui ne s'affichent pas sur l'app quand on lance gradle run
- Problèmes avec la DAO puisque la partie de la DAO concernant les addresses n'est pas terminée. Il faudra donc ajouter les adresses lors de la création d'un utilisateur à partir de la base de données

## DAY 2 (Mardi 09 janvier 2024)

### Objectifs de la journée

- Avoir une app qui tourne avec une page d'accueil, une page de connexion, une page d'inscription et une page de profil.
- Implémenté l'ajout d'un produit ou d'un service depuis l'app (front) jusqu'à la base de données (back)
- Relier ces pages avec le back (DAO)
- Continuer d'implémenter la DAO et de la tester


### Tâches réalisées


- Création des pages de connexion, d'inscription et de profil
- Création des premières méthodes de la DAO
- Amélioration de la base de données
- Front de la messagerie
- Début de la création des formulaires de produits et services


### Difficultés rencontrées

- Corrections de bugs sur la DAO
- Problèmes avec les images qui ne s'affichent pas sur l'app quand on lance gradle run
- Problèmes de suppression des utilisateurs dans la base de données lorsqu'ils ont encore des produits et des services.




