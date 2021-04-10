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

    public void run() {
        Message m;
        Bayes bayes = new Bayes(dico);
        System.out.println("Test :");

        // tests sur les spams
        for(int i = 0 ; i < nbSpamTests ; i++) {
            m = new Message(baseTestPath + "/spam/" + i + ".txt", dico);
            if(bayes.isSpam(m)) {
                System.out.println("SPAM numéro " + i + " identifié comme un SPAM");
            }else{
                System.out.println("SPAM numéro " + i + " identifié comme un HAM *** erreur ***");
            }
        }

        // tests sur les hams
        for(int i = 0 ; i < nbHamTests ; i++) {
            m = new Message(baseTestPath + "/ham/" + i + ".txt", dico);
            if(!bayes.isSpam(m)) {
                System.out.println("HAM numéro " + i + " identifié comme un HAM");
            }else{
                System.out.println("HAM numéro " + i + " identifié comme un SPAM *** erreur ***");
            }
        }

        System.out.println("Erreur de test sur les " + nbSpamTests + " SPAM : x%");
    }

}
