import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Message {

    private List<String> mots;
    private int[] vecteurDictionnaire;


    public Message(String emplacementMessage, Dictionnaire dico) {
        mots = new ArrayList<>(1000);
        lireMessage(emplacementMessage, dico);
        vecteurDictionnaire = dico.compareMessageDico(this.mots); // On récupère le vecteur de la présence ou non des mots du dictionnaire
    }

    /**
     * Lis le message à l'emplacement donné et compare ensuite avec la présence ou non du dictionnaire
     * @param emplacementMessage emplacement du message (peut-être à la racine)
     * @param dico Dictionnaire généré juste avant
     */
    private void lireMessage(String emplacementMessage, Dictionnaire dico) {
        File f = new File(emplacementMessage);
        String ligne;
        String[] contenuLigne;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(f));

            // On lit ligne par ligne
            while((ligne = bufferedReader.readLine()) != null){
                ligne = ligne.trim();
                contenuLigne = ligne.split("[.:;, |{}\"')(?<>/*0-9-@+$#\\[\\]_=`~!-]"); // On sépare la ligne des différents caractères non voulus présents dans le message
                for (String mot: contenuLigne) { // Pour chaque mot de la ligne
                    mot = mot.trim();
                    if(!mot.equals("") && mot.length() > 2){ // Il y a beaucoup de mots vides à cause du split et on en a pas besoin
                        mots.add(mot);
                    }
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Impossible de lire le fichier : " + emplacementMessage + ", fichier introuvable");
            return;
        } catch (IOException e) {
            System.err.println("Impossible de lire ou fermer le flux du fichier : " + emplacementMessage + ". Arret de la lecture.");
            return;
        }
        TraitementString.sort(mots); // On trie pour les optis
    }

    public int[] getVecteurDictionnaire() {
        return vecteurDictionnaire;
    }
}
