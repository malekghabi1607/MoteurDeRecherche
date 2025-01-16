# 🚀 Moteur de Recherche - Projet de Modélisation et POO 📖

## 📌 Objectif du Projet

Le but de ce projet est de concevoir un **moteur de recherche simplifié** capable de :
1. Gérer un vocabulaire et analyser un corpus (Wikipédia ou base d’ouvrages).
2. Calculer des scores de pertinence (TF-IDF) pour les documents.
3. Rechercher et afficher les documents les plus pertinents en fonction d’une requête.

## 🛠️ Fonctionnalités Principales

- **Analyse de Corpus** : Extraction des mots et construction d’un vocabulaire à partir des documents.
- **Gestion des Documents** : Représentation des documents et des mots associés.
- **Scores de Pertinence** : Calcul de scores TF-IDF et évaluation des similarités.
- **Recherche** : Affichage des documents les plus pertinents pour une requête donnée.

## 📁 Structure du Projet

- `base/` : Classes de base pour la gestion des mots, documents et corpus.
  - **`Mot`** : Représente un mot.
  - **`Document`** : Représente un document contenant un titre et une liste de mots.
  - **`Corpus`** : Gère un ensemble de documents et leur lecture depuis des fichiers.
  - **`DataSets`** : Enumération des sources disponibles (`WIKIPEDIA`, `OUVRAGES`).

- `recherche/` : Classes dédiées aux calculs de pertinence et à la recherche.
  - **`TfIdf`** : Calcule les scores TF-IDF.
  - **`Vocabulary`** : Gère le vocabulaire et les stop words.

- `calcul/` : Classes pour les calculs spécifiques.
  - **`TailleDocument`** : Calcule le nombre de documents dans un corpus.
  - **`TailleMot`** : Calcule le nombre total de mots dans un corpus.

- `exceptions/` : Classes pour la gestion des exceptions personnalisées.
  - **`CorpusException`** : Gère les erreurs liées aux corpus.

- `test/` : Classe principale (`Test`) pour orchestrer le programme.

## 📜 Fonctionnalités Détailées

### 🔎 Analyse et Indexation
- **Lecture des Corpus** : Chargement des fichiers au format `WIKIPEDIA` ou `OUVRAGES`.
- **Construction du Vocabulaire** :
  - Complet : Tous les mots du corpus.
  - Réduit : Exclut les mots considérés comme des stop words.

### 📊 Calcul des Scores de Pertinence
- **TF-IDF** : Évaluation des mots dans les documents en fonction de leur fréquence et de leur importance.
- **Traitement des Requêtes** : Calcul des similarités entre la requête et les documents.

### 🗂️ Gestion des Données
- Classe `Vocabulary` pour gérer les stop words et activer/désactiver le vocabulaire réduit.

### ⚙️ Interaction Utilisateur
- Sélection de la source (`WIKIPEDIA` ou `OUVRAGES`).
- Lecture des corpus et affichage des tailles.
- Recherche des documents les plus pertinents.

## 🛠️ Configuration de l’Environnement

- **IDE** : Eclipse ou IntelliJ IDEA.
- **Java** : Version 8.
- Organisation en **packages** pour structurer le code.

