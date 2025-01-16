package tests;

import base.Corpus;
import base.DataSets;
import calcul.TailleDocument;
import calcul.TailleMot;
import exceptions.CorpusException;
import recherche.TfIdf;
import recherche.Vocabulary;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {  
        Scanner scanner = new Scanner(System.in);

        // En-tête du projet
        afficherEnTete();

        try {
            // Étape 1 : Choisir une source pour le corpus
            DataSets dataSet = choisirSourceCorpus(scanner);

            // Étape 2 : Lecture de l'entrée utilisateur pour le nombre de lignes à analyser
            System.out.print("➡️ Entrez le nombre de lignes à analyser : ");
            int maxLines = scanner.nextInt();
            if (maxLines <= 0) {
                throw new IllegalArgumentException("❌ Le nombre de lignes doit être positif.");
            }

            // Simulation de chargement
            simulateProgress();

            // Étape 3 : Chargement et analyse du corpus
            Corpus corpus = chargerEtAnalyserCorpus(dataSet, maxLines);

            // Étape 4 : Calcul des tailles
            calculerTailleCorpus(corpus);

            // Étape 5 : Recherche de documents
            rechercherDocuments(scanner, corpus);

            // **Nouvelle Étape** : Recherche avec vocabulaire réduit
            System.out.println("\n➡️ Voulez-vous tester la recherche avec un vocabulaire réduit ? (oui/non)");
            scanner.nextLine(); // Consomme la ligne restante
            String reponse = scanner.nextLine().trim().toLowerCase();

            if (reponse.equals("oui")) {
                rechercherDocumentsAvecVocabulaireReduit(scanner, corpus);
            }

        } catch (CorpusException e) {
            System.err.println("\n❌ ERREUR : Problème avec le Corpus.");
            System.err.println("   Message : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n❌ ERREUR INATTENDUE : " + e.getMessage());
        } finally {
            scanner.close();
        }

        // Fin du programme
        afficherFin();
    }

    // Méthode pour afficher l'en-tête
    private static void afficherEnTete() {
        System.out.println("===================================================================================================================================================");
        System.out.println("                                                                🚀 Projet de Modélisation et POO 🚀                                               ");
        System.out.println("                                                                     🌟 Moteur de Recherche 🌟                                                      ");
        System.out.println("===================================================================================================================================================\n");
        System.out.println("🔎 OBJECTIF DU PROJET :");
        System.out.println("   Gérer un vocabulaire, calculer des scores de pertinence et rechercher des documents en fonction d'une requête.\n");
    }

    // Méthode pour choisir la source du corpus
    private static DataSets choisirSourceCorpus(Scanner scanner) {
        System.out.println("📖 SOURCES DU CORPUS :");
        System.out.println("   1️⃣ Wikipédia");
        System.out.println("   2️⃣ Base d’ouvrages littéraires");
        System.out.print("➡️ Choisissez une source (1 ou 2) : ");
        int choix = scanner.nextInt();

        switch (choix) {
            case 1:
                return DataSets.WIKIPEDIA;
            case 2:
                return DataSets.OUVRAGES;
            default:
                System.err.println("❌ Choix invalide. Par défaut, Wikipédia sera utilisé.");
                return DataSets.WIKIPEDIA;
        }
    }



    // Méthode pour simuler une barre de progression
    private static void simulateProgress() {
        String[] stages = {
            "[■□□□□□□□□□□] 10% en cours...",
            "[■■■■□□□□□□□□] 40% en cours...",
            "[■■■■■■■■□□□□] 70% en cours...",
            "[■■■■■■■■■■■] 100% Terminé !"
        };

        for (String stage : stages) {
            System.out.println(stage);
            try {
                Thread.sleep(500); // Pause pour chaque étape
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Partie A : Charger et analyser le corpus
    private static Corpus chargerEtAnalyserCorpus(DataSets dataSet, int maxLines) throws CorpusException {
        System.out.println("\n=============================================================== 📂 Partie A : ANALYSE DU CORPUS 📂 ====================================================\n");
        // Déterminer le chemin du fichier en fonction de l'ensemble de données sélectionné
        String cheminFichier;

        if (dataSet == DataSets.OUVRAGES) {
            cheminFichier = "src/tests/ressources/small_booksummaries.txt";
        } else if (dataSet == DataSets.WIKIPEDIA) {
            cheminFichier = "src/tests/ressources/small_stemmed.txt";
        } else {
            throw new IllegalArgumentException("Ensemble de données inconnu : " + dataSet);
        }

        // Charger et analyser le corpus
        Corpus corpus = new Corpus(cheminFichier, dataSet, maxLines);
        System.out.println("📜 Détails du Corpus :\n");
        System.out.println(corpus.toString());
        return corpus;
    }

    // Partie B : Calculer la taille du corpus
    private static void calculerTailleCorpus(Corpus corpus) {
        System.out.println("\n=============================================================== 📊 Partie B : CALCUL DE LA TAILLE 📊 ====================================================\n");

        // Calcul du nombre de documents
        TailleDocument tailleDocument = new TailleDocument();
        int nombreDocuments = corpus.taille(tailleDocument);
        System.out.println("📄 Nombre de documents dans le corpus : " + nombreDocuments);

        // Calcul du nombre total de mots
        TailleMot tailleMot = new TailleMot();
        int nombreMots = corpus.taille(tailleMot);
        System.out.println("📝 Nombre total de mots dans le corpus : " + nombreMots);
    }

    // Partie C : Rechercher des documents
    private static void rechercherDocuments(Scanner scanner, Corpus corpus) throws Exception {
        System.out.println("\n=============================================================== 📚 Partie C : RECHERCHE DE DOCUMENTS 📚 ====================================================\n");

        // Charger le vocabulaire
        Vocabulary vocabulary = Vocabulary.getInstance();
        vocabulary.collectFromCorpus(corpus);
        System.out.println("📖 Vocabulaire chargé avec succès.");
        System.out.println("       → Taille du vocabulaire : " + vocabulary.size());

        // Calcul des scores TF-IDF
        TfIdf tfIdf = new TfIdf();
        tfIdf.processCorpusFull(corpus);
        System.out.println("🔢 Scores TF-IDF calculés pour tous les documents.");

        // Traitement d'une requête
        System.out.print("\n➡️ Entrez une requête de recherche : ");
        scanner.nextLine(); // Consomme la ligne restante
        String query = scanner.nextLine();

        System.out.print("➡️ Entrez le nombre maximum de documents à afficher : ");
        int maxResults = scanner.nextInt();

        System.out.println("\n🔍 Traitement de la requête...");
        tfIdf.processQuery(query, maxResults);
    }
 



    // Partie D : Rechercher des documents avec vocabulaire réduit
    private static void rechercherDocumentsAvecVocabulaireReduit(Scanner scanner, Corpus corpus) throws Exception {
    	System.out.println("\n=============================================================== 📚 Partie D : DIMINUTION DU VOCABULAIRE 📚 ====================================================\n");
        Vocabulary vocab = Vocabulary.getInstance();
        vocab.chargerStopList("src/tests/ressources/stopWords.txt");
        vocab.collectReducedVocabulary(corpus);

        TfIdf tfIdf = new TfIdf();
        tfIdf.processCorpusReduced(corpus);

        afficherScoresTfIdf(tfIdf, corpus, true);

        System.out.print("\n➡️ Entrez une requête de recherche : ");
        scanner.nextLine();
        String query = scanner.nextLine();

        System.out.print("➡️ Entrez le nombre maximum de documents à afficher : ");
        int maxResults = scanner.nextInt();

        tfIdf.processQuery(query, maxResults);
    }

    // Méthode pour afficher les scores TF-IDF
    private static void afficherScoresTfIdf(TfIdf tfIdf, Corpus corpus, boolean vocabulaireReduit) {
        Vocabulary vocab = Vocabulary.getInstance();
        vocab.activerVocabulaireReduit(vocabulaireReduit);
        String mode = vocabulaireReduit ? "Vocabulaire Réduit" : "Vocabulaire Complet";

        System.out.println("\n==================== Scores TF-IDF (" + mode + ") ====================");
        for (base.Document doc : corpus) {
            System.out.println("Document : " + doc.getTitre());
            double[] tfVector = tfIdf.getTf(doc);
            double[] idfVector = tfIdf.getIdf();

            for (int i = 0; i < tfVector.length; i++) {
                String mot = vocab.getMot(i);
                if (mot != null) {
                    double tf = tfVector[i];
                    double idf = idfVector[i];
                    double tf_Idf = tf * idf;
                    System.out.printf("  Mot: %-15s | TF: %.4f | IDF: %.4f | TF-IDF: %.4f\n", mot, tf, idf, tf_Idf);
                    
                    
                }
            }
            System.out.println("-----------------------------------------------------");
        }
    }

    // Méthode pour afficher la fin
    private static void afficherFin() {
        System.out.println("\n=============================================================================================================================================================");
        System.out.println("                                                 ✅ Fin de l'Analyse du Corpus ✅                                                                         ");
        System.out.println("=============================================================================================================================================================");
    }
}