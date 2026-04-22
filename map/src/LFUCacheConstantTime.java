import java.util.*;

public class LFUCacheConstantTime {
    private final int capacity;
    private int min = -1;
    private final Map<Integer, Integer> vals;   // key, value
    private final Map<Integer, Integer> counts; // key, frequency
    private final Map<Integer, LinkedHashSet<Integer>> lists; // frequency, keys

    public LFUCacheConstantTime(int capacity) {
        this.capacity = capacity;
        this.vals = new HashMap<>();
        this.counts = new HashMap<>();
        this.lists = new HashMap<>();
        this.lists.put(1, new LinkedHashSet<>());
    }

    public int get(int key) {
        if (!vals.containsKey(key)) return -1;

        int count = counts.get(key);
        counts.put(key, count + 1);

        // Remove from current frequency list
        lists.get(count).remove(key);

        // Update min frequency if the current min list becomes empty
        if (count == min && lists.get(count).isEmpty()) {
            min++;
        }

        // Add to the next frequency list
        lists.computeIfAbsent(count + 1, k -> new LinkedHashSet<>()).add(key);
        return vals.get(key);
    }

    public void put(int key, int value) {
        if (capacity <= 0) return;

        if (vals.containsKey(key)) {
            vals.put(key, value);
            get(key); // trigger frequency update
            return;
        }

        if (vals.size() >= capacity) {
            // Evict the least frequent, least recently used item
            int evict = lists.get(min).iterator().next();
            lists.get(min).remove(evict);
            vals.remove(evict);
            counts.remove(evict);
        }

        // Add new item
        vals.put(key, value);
        counts.put(key, 1);
        min = 1;
        lists.get(1).add(key);
    }
}