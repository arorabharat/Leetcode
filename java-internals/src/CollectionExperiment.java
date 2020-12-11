import java.util.HashMap;
import java.util.HashSet;

public class CollectionExperiment {

    // java pass the value by reference
    static void passByRef(HashSet<String> set) {
        System.out.println(set.hashCode());
        set.add("new value");
    }

    public static void main(String args[]) {
        HashSet<String> set = new HashSet<>();
        set.add("bharat");
        set.add("arora");
        passByRef(set);
        System.out.println(set.hashCode());
        System.out.println(set.size());
    }
}
