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
    public void run() {
        Message m;
        Bayes bayes = new Bayes(dico);
        bayes.apprentissage("baseapp/");
        double nbErreurHam = 0;
        double nbErreurSpam = 0;

        System.out.println("Test :");

        // tests sur les spams
        for(int i = 0 ; i < nbSpamTests ; i++) {
            m = new Message(baseTestPath + "/spam/" + i + ".txt", dico);
            if(bayes.isSpam(m)) {
                System.out.println("SPAM numéro " + i + " identifié comme un SPAM");
            }else{
                System.out.println("SPAM numéro " + i + " identifié comme un HAM *** erreur ***");
                nbErreurSpam++;
            }
        }

        // tests sur les hams
        for(int i = 0 ; i < nbHamTests ; i++) {
            m = new Message(baseTestPath + "/ham/" + i + ".txt", dico);
            if(!bayes.isSpam(m)) {
                System.out.println("HAM numéro " + i + " identifié comme un HAM");
            }else{
                System.out.println("HAM numéro " + i + " identifié comme un SPAM *** erreur ***");
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
