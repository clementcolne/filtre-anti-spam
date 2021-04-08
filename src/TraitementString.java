import java.util.Comparator;
import java.util.List;

public class TraitementString {

    /**
     * Permet de trier les List de String
     * @param list liste de String
     */
    public static void sort(List<String> list){
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
    }
}
