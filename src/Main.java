import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
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
