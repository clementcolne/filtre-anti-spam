import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // On demande à l'utilisateur de rentrer les valeurs pour m_spam et m_ham
        Scanner scan = new Scanner(System.in);
        System.out.print("Veuillez entrer le nombre de messages spam utilisés pour l'apprentissage (m spam)\n");
        int mspam = scan.nextInt();
        System.out.print("Veuillez entrer le nombre de messages ham utilisés pour l'apprentissage (m ham)\n");
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
