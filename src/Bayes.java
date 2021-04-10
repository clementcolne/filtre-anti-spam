import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bayes {

    private double[] bjSpam;
    private double[] bjHam;
    double pySpam;
    private Dictionnaire dico;

    public Bayes(Dictionnaire dico) {
        bjSpam = new double[dico.tailleDictionnaire()];
        bjHam = new double[dico.tailleDictionnaire()];
        this.dico = dico;
    }

    /**
     * Apprends à partir du dossier en paramètre avec le nombre de messages en spam et ham
     * @param emplacementDossier emplacement du dossier qui contient les spam et les ham
     * @param nbMessagesSpam le nombre de messages en spam
     * @param nbMessagesHam le nombre de messages en ham
     */
    public void apprentissage(String emplacementDossier, int nbMessagesSpam, int nbMessagesHam){

        // En cas d'oubli
        if(!emplacementDossier.equals("") && !emplacementDossier.endsWith("/")){
            emplacementDossier += "/";
        }

        // Lit les spam/ham dans baseapp (liste de Message)
        readMessages(true, emplacementDossier, nbMessagesSpam); // On commence par les spam
        readMessages(false, emplacementDossier, nbMessagesHam); // On enchaîne sur les ham
        pySpam = (double)nbMessagesSpam/(double)(nbMessagesHam+nbMessagesSpam);
    }

    public void apprentissage(String emplacementDossier){
        apprentissage(emplacementDossier, 100, 100);
    }

    /**
     * Lis les messages comprit entre 0.txt et nbMessages.txt
     * @param spam si c'est le dossier de spam ou pas
     * @param emplacementDossier emplacement du dossier qui contient les dossier de ham et spam
     * @param nbMessages le nombre de message à lire dans le dossier
     */
    private void readMessages(boolean spam,String emplacementDossier, int nbMessages){
        Message message;

        double[] bj = new double[dico.tailleDictionnaire()];

        String emplacementDossierSpamHam;
        if(spam){
            emplacementDossierSpamHam = emplacementDossier + "spam/";
        }else{
            emplacementDossierSpamHam = emplacementDossier + "ham/";
        }

        int[] vecteur;
        for(int i = 0; i < nbMessages; i++){
            message = new Message(emplacementDossierSpamHam + i + ".txt", dico); // On lit le message x.txt

            vecteur = message.getVecteurDictionnaire(); // On récupère le vecteur du dictionnaire

            for (int j = 0; j < vecteur.length; j++) {
                bj[j] += (vecteur[j]>0?1:0); // Si le vecteur est supérieur à 0 alors le mot apparait dans le message
            }
        }

        double epsi = 0.1;

        // calcul de bj
        for (int i = 0; i < dico.tailleDictionnaire(); i++) {
            bj[i] += epsi;
            bj[i] = bj[i]/((double)nbMessages + 2*epsi); // On en réduit à des probabilités
        }

        if(spam){
            bjSpam = Arrays.copyOf(bj, bj.length);
        }else{
            bjHam = Arrays.copyOf(bj, bj.length);
        }
    }


    /**
     * Vérifie si le message est un spam avec le classifieur de Bayes
     * @param message message à analyser
     * @return si le message est un spam ou non
     */
    public boolean isSpam(Message message){
        int[] vecteur = message.getVecteurDictionnaire();
        double spam = pySpam;
        double ham = 1 - pySpam;

        // On parcourt le vecteur dictionnaire du message
        for (int i = 0; i < vecteur.length; i++) {
            if(vecteur[i] > 0){ // S'il contient le mot alors on garde les proba normales sinon on prends les inverses
                spam *= bjSpam[i];
                ham *= bjHam[i];
            }else{
                spam *= 1-bjSpam[i];
                ham *= 1-bjHam[i];
            }
        }
        return spam > ham;
    }
}
