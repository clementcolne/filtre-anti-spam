import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length < 3) {
            System.err.println("Erreur au lancement du programme\n" +
                    "Veuillez respecter la structure \"java filtreAntiSpam <chemin de la base de tests> <nombre de spam pour les tests> <nombre de ham pour les tests>\"");
            System.exit(1);
        }
        // on récupère le chemin de la base de tests
        String baseTestPath = args[0];
        // on récupère le nombre de spams utilisés dans les tests
        int nbSpamTests = Integer.parseInt(args[1]);
        // on récupère le nombre de hams utilisés dans les tests
        int nbHamTests = Integer.parseInt(args[2]);

        // On demande à l'utilisateur de rentrer les valeurs pour m_spam et m_ham
        Scanner scan = new Scanner(System.in);
        System.out.print("Combien de SPAM dans la base d’apprentissage ?\n");
        int mspam = scan.nextInt();
        System.out.print("Combien de HAM dans la base d’apprentissage ?\n");
        int mham = scan.nextInt();

        Dictionnaire dico = charger_dictionnaire();

        Message mess = lire_message(dico);
        System.out.println(Arrays.toString(mess.getVecteurDictionnaire()));
    }

    public static Dictionnaire charger_dictionnaire(){
        return new Dictionnaire("dictionnaire1000en.txt");
    }

    public static Message lire_message(Dictionnaire dico){
        return new Message("0.txt", dico);
    }
}
