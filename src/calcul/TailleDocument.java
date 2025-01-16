package calcul;

import base.Corpus;
//calculer le nombre de documents présents dans un corpus.
public class TailleDocument {
    public int calculer(Corpus corpus) {
        return corpus.size(); // Retourne la taille du Vector (nombre de documents)
    }
}