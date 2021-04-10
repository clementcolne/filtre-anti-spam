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

    public void apprentissage(String emplacementDossier, int nbMessagesSpam, int nbMessagesHam){

        // Lit les spam/ham dans baseapp (liste de Message)
        if(!emplacementDossier.equals("") && !emplacementDossier.endsWith("/")){
            emplacementDossier += "/";
        }

        ArrayList<Message> messages = new ArrayList<>(nbMessagesSpam);
        readMessages(true, emplacementDossier, nbMessagesSpam, messages);
        readMessages(false, emplacementDossier, nbMessagesHam, messages);
        pySpam = (double)nbMessagesSpam/(double)(nbMessagesHam+nbMessagesSpam);
    }

    public void apprentissage(String emplacementDossier){
        apprentissage(emplacementDossier, 100, 100);
    }

    private void readMessages(boolean spam,String emplacementDossier, int nbMessages, List<Message> listMessage){
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
            //fichier = new File(emplacementDossier + i + ".txt");
            message = new Message(emplacementDossierSpamHam + i + ".txt", dico);
            listMessage.add(message);

            vecteur = message.getVecteurDictionnaire();

            for (int j = 0; j < vecteur.length; j++) {
                bj[j] += (vecteur[j]>0?1:0);
            }
        }
        // calcul de bjSpam
        for (int i = 0; i < dico.tailleDictionnaire(); i++) {
            if(bj[i]/(double)nbMessages > 1){
                System.out.println(bj[i]);
            }
            bj[i] = bj[i]/(double)nbMessages;
        }

        if(spam){
            bjSpam = Arrays.copyOf(bj, bj.length);
        }else{
            bjHam = Arrays.copyOf(bj, bj.length);
        }
    }

    public boolean isSpam(Message message){
        int[] vecteur = message.getVecteurDictionnaire();
        double spam = pySpam;
        double ham = 1 - pySpam;
        for (int i = 0; i < vecteur.length; i++) {
            if(vecteur[i] > 0){
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
