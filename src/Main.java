import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Dictionnaire dico = charger_dictionnaire();
        Bayes bayes;
        Message mes;
        switch (args[0]) {
            case "-a":
                if(args.length != 5) {
                    erreur();
                }
                // On souhaite effectuer tout l'apprentissage et enregistrer le classifieur dans le fichier spécifié en argument
                String fichierSauvegardeClassifieur = args[1];
                String baseAppPath = args[2];
                int nbSpamApp = Integer.parseInt(args[3]);
                int nbHamApp = Integer.parseInt(args[4]);
                bayes = new Bayes(dico);
                bayes.apprentissage(baseAppPath, nbSpamApp, nbHamApp, fichierSauvegardeClassifieur);
                System.out.println("Le classifieur a bien été enregistré.");
                break;
            case "-m":
                if(args.length != 3) {
                    erreur();
                }
                // On souhaite tester rapidement un message sur le classifieur connu
                String cheminSauvegardeClassifieur = args[1];
                String message = args[2];
                bayes = new Bayes(cheminSauvegardeClassifieur, dico);
                mes = new Message(message, dico);
                System.out.print("Le message a été identifié comme un ");
                if(!bayes.isSpam(mes)) {
                    System.out.println("HAM");
                }else{
                    System.out.println("SPAM");
                }
                break;
            case "-t":
                if(args.length != 4) {
                    erreur();
                }
                // on souhaite effectuer tout l'apprentissage et tester sur une base de tests donnée en paramètre
                String baseTestPath = args[1];
                int nbSpamTest = Integer.parseInt(args[2]);
                int nbHamTest = Integer.parseInt(args[3]);

                // On demande à l'utilisateur de rentrer les valeurs pour m_spam et m_ham
                Scanner scan = new Scanner(System.in);
                System.out.print("Combien de SPAM dans la base d’apprentissage ?\n");
                int mspam = scan.nextInt();
                System.out.print("Combien de HAM dans la base d’apprentissage ?\n");
                int mham = scan.nextInt();

                // Apprentissage
                bayes = new Bayes(dico);
                bayes.apprentissage("baseapp/", mspam, mham);

                Tests tests = new Tests(dico, baseTestPath, nbHamTest, nbSpamTest);
                // lancement des tests
                tests.run(bayes);
                break;
            case "-n":
                if(args.length != 4) {
                    erreur();
                }
                // On souhaite ajouter un nouveau message au classifieur connu
                cheminSauvegardeClassifieur = args[1];
                message = args[2];
                String type = args[3];
                bayes = new Bayes(cheminSauvegardeClassifieur, dico);
                mes = new Message(message, dico);
                bayes.newApprentissage(mes, type, cheminSauvegardeClassifieur);
                System.out.println("Le nouveau message a été ajouté à la base d'apprentissage.");
                break;
            default:
                // erreur, les arguments spécifiés sont incorrects
                erreur();
                break;
        }

    }

    public static Dictionnaire charger_dictionnaire(){
        return new Dictionnaire("dictionnaire1000en.txt");
    }

    public static void erreur() {
        System.err.println("Erreur arguments incorrects\n" +
                "test :\t\t\t\t -t <chemin de la base de tests> <nombre de spam pour les tests> <nombre de ham pour les tests>\n" +
                "apprentissage :\t\t -a <chemin du classifieur enregistré> <chemin de la base de tests> <nombre de spam pour les tests> <nombre de ham pour les tests>\n" +
                "message :\t\t\t -m <chemin du classifieur enregistré> <chemin du message à tester>\n" +
                "nouveau message :\t -n <chemin du classifieur enregistré> <chemin du nouveau message à ajouter> <etiquette du nouveau message>\n" +
                "");
        System.exit(1);
    }
}
