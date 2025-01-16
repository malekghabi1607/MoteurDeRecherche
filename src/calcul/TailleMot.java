package calcul;

import base.Corpus;
import base.Document;
//Compte le nombre total de mots dans tous les documents d’un corpus.
public class TailleMot {
    public int calculer(Corpus corpus) {
        int totalMots = 0;
        for (Document document : corpus) {
            totalMots += document.size(); // document.size() retourne le nombre de mots dans un document
        }
        return totalMots;
    }
}