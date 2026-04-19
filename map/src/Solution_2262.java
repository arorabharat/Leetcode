import java.util.*;

public class Solution_2262 {

    static class Entry {
        int expiryTime;
        int value;

        public Entry(int expiryTime, int value) {
            this.expiryTime = expiryTime;
            this.value = value;
        }
    }

    static class Cache {

        Map<Integer, Entry> keyToEntry;
        NavigableMap<Integer, Integer> time2Key;

        private boolean isExpired(int t, Entry entry) {
            return entry.expiryTime < t;
        }

        public Cache() {
            this.keyToEntry = new HashMap<>();
            this.time2Key = new TreeMap<>();
        }

        boolean set(int t, int k, int v, int e) {
            boolean doesExist = doesValidEntryExists(t, k);
            this.keyToEntry.put(k, new Entry(t + e, v));
            this.time2Key.put(t + e, k);
            return doesExist;
        }

        private boolean doesValidEntryExists(int t, int k) {
            if (this.keyToEntry.containsKey(k)) {
                Entry entry = this.keyToEntry.get(k);
                if (!isExpired(t, entry)) {
                    return true;
                }
            }
            // this.keyToEntry.remove(k);
            return false;
        }

        int get(int t, int k) {
            if (doesValidEntryExists(t, k)) {
                Entry entry = this.keyToEntry.get(k);
                return entry.value;
            }
            return -1;
        }

        int count(int t) {
            if (time2Key.isEmpty() || t > time2Key.lastKey()) {
                return 0;
            }
            return time2Key.subMap(t, true, time2Key.lastKey(), true).size();
        }
    }

    static void main() {
        Cache cache = new Cache();
        System.out.println(cache.set(0, 1, 42, 100));
        System.out.println(cache.get(50, 1));
        System.out.println(cache.count(50));
        System.out.println(cache.get(150, 1));

        System.out.println("===========");

        Cache cache2 = new Cache();
        System.out.println(cache2.set(0, 1, 42, 50));
        System.out.println(cache2.set(40, 1, 50, 100));
        System.out.println(cache2.get(50, 1));
        System.out.println(cache2.get(120, 1));
        System.out.println(cache2.get(200, 1));
        System.out.println(cache2.count(250));
    }

    class Cache2 {

        Map<Integer, Entry> keyMap = new HashMap<>();
        TreeMap<Integer, Set<Integer>> expiryMap = new TreeMap<>();

        public boolean set(int t, int k, int v, int e) {

            boolean exists = get(t,k) != -1;

            if (keyMap.containsKey(k)) {
                int oldExpiry = keyMap.get(k).expiryTime;
                Set<Integer> set = expiryMap.get(oldExpiry);
                set.remove(k);
                if (set.isEmpty()) expiryMap.remove(oldExpiry);
            }

            int expiry = t + e;

            keyMap.put(k, new Entry(expiry, v));

            expiryMap.computeIfAbsent(expiry,x->new HashSet<>()).add(k);

            return exists;
        }

        public int get(int t, int k) {

            Entry entry = keyMap.get(k);

            if (entry == null || entry.expiryTime < t) {
                return -1;
            }

            return entry.value;
        }

        public int count(int t) {

            int total = 0;

            for (Set<Integer> set : expiryMap.tailMap(t, true).values()) {
                total += set.size();
            }

            return total;
        }
    }
}
