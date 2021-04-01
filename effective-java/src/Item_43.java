import java.util.HashMap;
import java.util.Map;

public class Item_43 {
    public static void main(String[] args) {
        Map<Integer, Integer> keyCount = new HashMap<>();
        keyCount.merge(10, 2, Integer::sum);
        keyCount.merge(10, 1, Integer::sum);
        System.out.println(keyCount.get(10));

        Map<String, String> map = new HashMap<>();
        map.put("Name", "Aman");
        map.put("Address", "Kolkata");
        System.out.println("Map: " + map);
        map.compute("Name", (k, v) -> (v == null) ? "#" : v.concat("#"));
        map.compute("Address", (k, v) -> (v == null) ? "#" : v.concat("#"));
        map.compute("Phone", (k, v) -> (v == null) ? "#" : v.concat("#"));
        System.out.println("New Map: " + map);
    }
}
