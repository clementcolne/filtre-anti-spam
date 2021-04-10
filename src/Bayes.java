import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Bayes {

    private double[] bjSpam;
    private double[] bjHam;
    private Dictionnaire dico;

    public Bayes(Dictionnaire dico) {
        bjSpam = new double[dico.tailleDictionnaire()];
        bjHam = new double[dico.tailleDictionnaire()];
        this.dico = dico;
    }

    public void apprentissage(String emplacementDossier){

        // Lit les spam/ham dans baseapp (liste de Message)
        ArrayList<String> dictionnaire = new ArrayList<>(1000);
        int nbMessagesSpam = 100;
        int nbMessagesHam = 100;
        File fichier;
        BufferedReader bufferedReader;
        if(!emplacementDossier.endsWith("/")){
            emplacementDossier += "/";
        }
        ArrayList<Message> messages = new ArrayList<>(100);
        Message message;
        for(int i = 0; i < nbMessagesSpam; i++){
            //fichier = new File(emplacementDossier + i + ".txt");
            message = new Message(emplacementDossier + i + ".txt", dico);
            messages.add(message);
            bjSpam = dico.updateOccu(message, bjSpam);
        }
        // calcul de bjSpam
    }

    public boolean isSpam(Message message){

        return false;
    }
}
