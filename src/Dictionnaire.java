import java.io.*;
import java.util.*;

public class Dictionnaire {

    private String[] contenu;

    public Dictionnaire(String emplacementFichier) {
        contenu = new String[1];
        chargerDictionnaire(emplacementFichier);
    }

    /**
     * Génère le vecteur du nombre de mots présent dans le dictionnaire
     * @param mots la liste des mots du message
     * @return le vecteur des mots présents ou non
     */
    public int[] compareMessageDico(List<String> mots){
        int indiceMots;
        int indiceDico;
        int lastIndiceDico = 0; // Vu que les deux tableaux sont triés, on a plus besoin de passer sur les éléments du dictionnaire déjà vu
        int[] vecteurMots = new int[contenu.length];
        String mot;
        for(indiceMots = 0; indiceMots < mots.size();){ // On parcours la liste des mots (la gestion de l'indice intervient plus tard
            indiceDico = lastIndiceDico;
            while(indiceDico < contenu.length && !contenu[indiceDico].equalsIgnoreCase(mots.get(indiceMots))){ // On parcourt le dictionnaire
                indiceDico++;
            }
            if(indiceDico >= contenu.length){ // Si on a pas trouvé de mot dans le dico
                mot = mots.get(indiceMots); // On récpère le mot actuel
                do{ // On passe au prochain indice pour voir si le mot apparait plusieurs fois
                    indiceMots++;
                }while(indiceMots < mots.size() && mot.equalsIgnoreCase(mots.get(indiceMots)));
            }else{
                lastIndiceDico = indiceDico;
                mot = contenu[indiceDico]; // On récpère le mot du dico
                do{ // On passe au prochain indice pour voir si le mot apparait plusieurs fois (et on compte)
                    indiceMots++;
                    vecteurMots[indiceDico]++;
                }while(indiceMots < mots.size() && mot.equalsIgnoreCase(mots.get(indiceMots)));
            }
        }
        return vecteurMots;
    }

    /**
     * Charge le dictionnaire à l'emplacement du fichier
     * Le dictionnaire doit contenir que des mots séparé par des retour à la ligne
     *
     * @param emplacementFichier emplacement du fichier, peut être à la racine du projet
     */
    private void chargerDictionnaire(String emplacementFichier) {
        ArrayList<String> dictionnaire = new ArrayList<>(1000);
        File fichier = new File(emplacementFichier);
        String ligne;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fichier));

            // On lit ligne par ligne
            while((ligne = bufferedReader.readLine()) != null){
                ligne = ligne.trim(); // On trim la ligne
                if(!ligne.equals("") && ligne.length() > 2){ // On sait jamais (pas d'espace vide dans le dictionnaire)
                    dictionnaire.add(ligne);
                }
            }

            TraitementString.sort(dictionnaire); // Nécessaire pour des optis
            contenu = dictionnaire.toArray(contenu); // On récupère la version tableau statique

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Impossible de lire le fichier de dictionnaire, dictionnaire introuvable");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Impossible de lire ou fermer le flux du fichier. Arret de la lecture.");
            e.printStackTrace();
        }
        System.out.println("Lecture du dictionnaire terminée");
    }

    public int tailleDictionnaire(){
        return contenu.length;
    }
}
