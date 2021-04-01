import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

public class Item_42 {

    // first rule of lambda - if you don't know what to pass as lambda arg, use anonymous class
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("Some");
        words.add("of");
        words.add("us");
        words.add("don't");
        words.add("realise");

        // sorting the collection using the anonymous comparator
        Collections.sort(words, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        words.forEach(System.out::println);

        List<String> words2 = new ArrayList<>();
        words2.add("Some");
        words2.add("of");
        words2.add("us");
        words2.add("don't");
        words2.add("realise");

        // Lambda expression as function object (replaces anonymous class)
        Collections.sort(words2, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

        Collections.sort(words2, Comparator.comparingInt(new ToIntFunction<String>() {
            @Override
            public int applyAsInt(String value) {
                return value.length();
            }
        }));

        Collections.sort(words2, Comparator.comparingInt(String::length));

        words2.forEach(System.out::println);

    }
}
