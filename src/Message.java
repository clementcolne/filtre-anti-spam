import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Message {

    private String[] mots;
    private int[] vecteurDictionnaire;


    public Message(String emplacementMessage, Dictionnaire dico) {
        mots = new String[0];
        lireMessage(emplacementMessage, dico);
        System.out.println(Arrays.toString(mots));
    }

    /**
     * Lis le message à l'emplacement donné et compare ensuite avec la présence ou non du dictionnaire
     * @param emplacementMessage emplacement du message (peut-être à la racine)
     * @param dico Dictionnaire généré juste avant
     */
    private void lireMessage(String emplacementMessage, Dictionnaire dico) {
        ArrayList<String> arrayLMots = new ArrayList<>(1000);
        File f = new File(emplacementMessage);
        String ligne;
        String[] contenuLigne;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));

            // On lit ligne par ligne
            while((ligne = bufferedReader.readLine()) != null){
                ligne = ligne.trim();
                contenuLigne = ligne.split("[.:;, |{}\"')(?<>/*0-9-@+$#\\[\\]_-]"); // On sépare la ligne des différents caractères non voulus présents dans le message
                for (String mot: contenuLigne) { // Pour chaque mot de la ligne
                    mot = mot.trim();
                    if(!mot.equals("")){ // Il y a beaucoup de mots vides à cause du split et on en a pas besoin
                        arrayLMots.add(mot);
                    }
                }
            }

            TraitementString.sort(arrayLMots); // On trie pour les optis

            this.mots = arrayLMots.toArray(this.mots); // Transformation de l'arraylist en talbeau statique

            vecteurDictionnaire = dico.compareMessageDico(this.mots); // On récupère le vecteur de la présence ou non des mots du dictionnaire

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Impossible de lire le fichier de message : " + emplacementMessage + ", message introuvable");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Impossible de lire ou fermer le flux du fichier : " + emplacementMessage + ". Arret de la lecture.");
            e.printStackTrace();
        }
        System.out.println(mots.length + " mots soutirés du message");
    }

    public int[] getVecteurDictionnaire() {
        return vecteurDictionnaire;
    }
}
