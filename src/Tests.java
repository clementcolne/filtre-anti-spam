public class Tests {

    private Dictionnaire dico;
    private int nbHamTests;
    private int nbSpamTests;
    private String baseTestPath;

    public Tests(Dictionnaire dico, String baseTestPath, int nbHamTests, int nbSpamTests) {
        this.dico = dico;
        this.nbHamTests = nbHamTests;
        this.nbSpamTests = nbSpamTests;
        this.baseTestPath = baseTestPath;
    }

    /**
     * Lance les tests et fait les affichages
     */
    public void run(Bayes bayes) {
        Message m;
        double nbErreurHam = 0;
        double nbErreurSpam = 0;

        System.out.println("Test :");

        // tests sur les spams
        for(int i = 0 ; i < nbSpamTests ; i++) {
            m = new Message(baseTestPath + "/spam/" + i + ".txt", dico);
            System.out.println("SPAM numéro " + i + " : P(Y=SPAM | X=x) = " + bayes.getpSpam() + ", P(Y=HAM | X=x) = "+ bayes.getpHam());
            if(bayes.isSpam(m)) {
                System.out.println("\t\t\t\t=> identifié comme un SPAM\n");
            }else{
                System.out.println("\t\t\t\t=> identifié comme un HAM *** erreur ***\n");
                nbErreurSpam++;
            }
        }

        // tests sur les hams
        for(int i = 0 ; i < nbHamTests ; i++) {
            m = new Message(baseTestPath + "/ham/" + i + ".txt", dico);
            System.out.println("HAM numéro " + i + " : P(Y=SPAM | X=x) = " + bayes.getpSpam() + ", P(Y=HAM | X=x) = "+ bayes.getpHam());
            if(!bayes.isSpam(m)) {
                System.out.println("\t\t\t\t=> identifié comme un HAM\n");
            }else{
                System.out.println("\t\t\t\t=> identifié comme un SPAM *** erreur ***\n");
                nbErreurHam++;
            }
        }

        // calcul du taux d'erreur sur les spams
        double tauxErreurSpam = (nbErreurSpam/nbSpamTests) * 100;
        System.out.println("Erreur de test sur les " + nbSpamTests + " SPAM : " + tauxErreurSpam + " %");

        // calcul du taux d'erreur sur les hams
        double tauxErreurHam = (nbErreurHam/nbHamTests) * 100;
        System.out.println("Erreur de test sur les " + nbHamTests + " HAM :  " + tauxErreurHam + " %");

        // calcul du taux d'erreur global
        double tauxErreurGlobal = (nbErreurSpam+nbErreurHam)/(nbHamTests+nbSpamTests) * 100;
        System.out.println("Erreur de test globale sur " + (nbSpamTests+nbHamTests) + " : " + tauxErreurGlobal + " %");
    }

}
