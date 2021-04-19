import java.io.*;
import java.util.Arrays;

public class Bayes implements Serializable {

    private int[] bjSpam;
    private int[] bjHam;
    private double pySpam;
    private Dictionnaire dico;
    private double pSpam = 0.0;
    private double pHam = 0.0;
    private int nbMessagesSpam = 0;
    private int nbMessagesHam = 0;
    private double epsi = 0.1;

    public Bayes(Dictionnaire dico) {
        bjSpam = new int[dico.tailleDictionnaire()];
        bjHam = new int[dico.tailleDictionnaire()];
        this.dico = dico;
    }

    /**
     * Constructeur qui lit à partir du fichier
     * @param emplacementFichier emplacement du fichier à lire
     * @param dico le dictionnaire utilisé
     */
    public Bayes(String emplacementFichier, Dictionnaire dico){
        this.dico = dico;
        bjSpam = new int[dico.tailleDictionnaire()];
        bjHam = new int[dico.tailleDictionnaire()];
        File f = new File(emplacementFichier);
        String mot;
        String[] contenuLignebjSpam;
        String[] contenuLignebjHam;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(f));

            // On lit les deux premières lignes qui sont les occurences des mots en spam et ham respectivement
            contenuLignebjSpam = bufferedReader.readLine().trim().split("[ ]"); // On sépare la ligne des différents caractères non voulus présents dans le message
            contenuLignebjHam = bufferedReader.readLine().trim().split("[ ]");
            for (int i = 0; i < contenuLignebjSpam.length && i < bjSpam.length; i++) { // Pour chaque mot de la ligne
                mot = contenuLignebjSpam[i].trim();
                bjSpam[i] = Integer.parseInt(mot);
                mot = contenuLignebjHam[i].trim();
                bjHam[i] = Integer.parseInt(mot);
            }

            // Les deux lignes sont les nombre de messages en spam et en ham respectivement
            mot = bufferedReader.readLine().trim();
            nbMessagesSpam = Integer.parseInt(mot);
            mot = bufferedReader.readLine().trim();
            nbMessagesHam = Integer.parseInt(mot);

            pySpam = (double)nbMessagesSpam/(double)(nbMessagesHam+nbMessagesSpam);

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Impossible d'écrire sur le fichier : " + emplacementFichier + ", fichier introuvable");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Impossible de écrire ou fermer le flux du fichier : " + emplacementFichier + ". Arret de l'écriture.");
            System.exit(1);
        }
    }

    /**
     * Sauvegarde le classifieur sur l'emplacement du fichier
     * @param emplacementFichier l'emplacement du fichier à sauvegarder
     */
    public void sauvegarde(String emplacementFichier){
        File f = new File(emplacementFichier);
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(f));

            for(Integer d : bjSpam){
                bufferedWriter.write(d.toString() + " ");
            }
            bufferedWriter.newLine();
            for(Integer d : bjHam){
                bufferedWriter.write(d.toString() + " ");
            }

            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(nbMessagesSpam));
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(nbMessagesHam));

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Apprends à partir du dossier en paramètre avec le nombre de messages en spam et ham
     * @param emplacementDossier emplacement du dossier qui contient les spam et les ham
     * @param nbMessagesSpam le nombre de messages en spam
     * @param nbMessagesHam le nombre de messages en ham
     * @param fichierSauvegardeClassifieur Emplacememnt du fichier où l'on enregistre le classifieur
     */
    public void apprentissage(String emplacementDossier, int nbMessagesSpam, int nbMessagesHam, String fichierSauvegardeClassifieur){
        apprentissage(emplacementDossier, nbMessagesSpam, nbMessagesHam);

        if(!fichierSauvegardeClassifieur.equals("")){
            sauvegarde(fichierSauvegardeClassifieur);
        }
    }

    /**
     * Apprends à partir du dossier en paramètre avec le nombre de messages en spam et ham
     * @param emplacementDossier emplacement du dossier qui contient les spam et les ham
     * @param nbMessagesSpam le nombre de messages en spam
     * @param nbMessagesHam le nombre de messages en ham
     */
    public void apprentissage(String emplacementDossier, int nbMessagesSpam, int nbMessagesHam){
        this.nbMessagesSpam = nbMessagesSpam;
        this.nbMessagesHam = nbMessagesHam;

        // En cas d'oubli
        if(!emplacementDossier.equals("") && !emplacementDossier.endsWith("/")){
            emplacementDossier += "/";
        }

        // Lit les spam/ham dans baseapp (liste de Message)
        readMessages(true, emplacementDossier, nbMessagesSpam); // On commence par les spam
        readMessages(false, emplacementDossier, nbMessagesHam); // On enchaîne sur les ham
        pySpam = (double)nbMessagesSpam/(double)(nbMessagesHam+nbMessagesSpam);
    }

    /**
     * Lis les messages comprit entre 0.txt et nbMessages.txt
     * @param spam si c'est le dossier de spam ou pas
     * @param emplacementDossier emplacement du dossier qui contient les dossier de ham et spam
     * @param nbMessages le nombre de message à lire dans le dossier
     */
    private void readMessages(boolean spam, String emplacementDossier, int nbMessages){
        Message message;

        int[] bj = new int[dico.tailleDictionnaire()];

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
            if(vecteur != null){
                for (int j = 0; j < vecteur.length; j++) {
                    bj[j] += (vecteur[j]>0?1:0); // Si le vecteur est supérieur à 0 alors le mot apparait dans le message
                }
            }
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
        double pSpamMot;
        double pHamMot;
        if(vecteur != null){
            pSpam = pySpam;
            pHam = 1 - pySpam;

            // On parcourt le vecteur dictionnaire du message
            for (int i = 0; i < vecteur.length; i++) {
                pSpamMot = (epsi + (double)bjSpam[i])/((double)nbMessagesSpam + 2*epsi);
                pHamMot = (epsi + (double)bjHam[i])/((double)nbMessagesHam + 2*epsi);
                if(vecteur[i] > 0){ // S'il contient le mot alors on garde les proba normales sinon on prends les inverses
                    pSpam *= pSpamMot;
                    pHam *= pHamMot;
                }else{
                    pSpam *= 1-pSpamMot;
                    pHam *= 1-pHamMot;
                }
            }

            return pSpam > pHam;
        }
        return pySpam > 0.5;
    }

    public double getpSpam() {
        return pSpam;
    }
    public double getpHam() {
        return pHam;
    }

    /**
     * Nouveau apprentissage d'un nouveau message
     * @param message Le message à apprendre
     * @param type Le type du nouveau messages (doit être égal à "SPAM" ou "HAM")
     * @param cheminSauvegardeClassifieur Le chemin du classifieur (pour pouvoir l'enregistrer)
     */
    public void newApprentissage(Message message, String type, String cheminSauvegardeClassifieur) {
        int[] vecteur = message.getVecteurDictionnaire(); // On récupère le vecteur du dictionnaire
        if(vecteur != null){
            if(type.equals("SPAM")){
                this.nbMessagesSpam++;
                for (int j = 0; j < vecteur.length; j++) {
                    bjSpam[j] += (vecteur[j]>0?1:0); // Si le vecteur est supérieur à 0 alors le mot apparait dans le message
                }
            }else{
                this.nbMessagesHam++;
                for (int j = 0; j < vecteur.length; j++) {
                    bjHam[j] += (vecteur[j]>0?1:0); // Si le vecteur est supérieur à 0 alors le mot apparait dans le message
                }
            }
        }

        sauvegarde(cheminSauvegardeClassifieur);
    }
}
