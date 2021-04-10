import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Dictionnaire dico = charger_dictionnaire();


        Bayes bayes = new Bayes(dico);
        bayes.apprentissage("baseapp/");
    }

    public static Dictionnaire charger_dictionnaire(){
        return new Dictionnaire("dictionnaire1000en.txt");
    }
}
