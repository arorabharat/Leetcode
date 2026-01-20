import java.util.*;

public class Solution_432 {
    class AllOne {

        TreeMap<Integer, Set<String>> sortedKeys = new TreeMap<>();
        Map<String, Integer> count = new HashMap<>();

        public AllOne() {
        }

        private void updateTreeMap(TreeMap<Integer, Set<String>> map, int prev, int next, String key) {

            if (prev > 0 && map.containsKey(prev)) {
                map.get(prev).remove(key);
                if (map.get(prev).isEmpty()) {
                    map.remove(prev);
                }
            }

            if (next > 0) {
                if (!map.containsKey(next)) {
                    map.put(next, new HashSet<>());
                }
                map.get(next).add(key);
            }

        }

        public void inc(String key) {
            if (count.containsKey(key)) {
                count.put(key, count.get(key) + 1);
            } else {
                count.put(key, 1);
            }
            int next = count.get(key);
            updateTreeMap(sortedKeys, next - 1, next, key);
        }

        public void dec(String key) {
            if (count.containsKey(key)) {
                int prev = count.get(key);
                if (count.get(key) > 1) {
                    count.put(key, prev - 1);
                } else {
                    count.remove(key);
                }
                updateTreeMap(sortedKeys, prev, prev - 1, key);
            }

        }

        public String getMaxKey() {
            return sortedKeys.isEmpty() ? "" : sortedKeys.lastEntry().getValue().iterator().next();
        }

        public String getMinKey() {
            return sortedKeys.isEmpty() ? "" : sortedKeys.firstEntry().getValue().iterator().next();
        }
    }

    class AllOne_2 {

        // count -> set of keys
        private final TreeMap<Integer, Set<String>> freqMap = new TreeMap<>();
        // key -> count
        private final Map<String, Integer> keyCount = new HashMap<>();

        public AllOne_2() {
        }

        /* ---------- Helpers ---------- */

        private void removeFromBucket(int freq, String key) {
            Set<String> keys = freqMap.get(freq);
            if (keys == null) return;
            keys.remove(key);
            if (keys.isEmpty()) {
                freqMap.remove(freq);
            }
        }

        private void addToBucket(int freq, String key) {
            if (freq <= 0) return;
            freqMap.computeIfAbsent(freq, f -> new HashSet<>())
                    .add(key);
        }

        private void moveKey(String key, int prev, int next) {
            removeFromBucket(prev, key);
            addToBucket(next, key);
        }

        /* ---------- Operations ---------- */

        public void inc(String key) {
            int prev = keyCount.getOrDefault(key, 0);
            int next = prev + 1;
            keyCount.put(key, next);
            moveKey(key, prev, next);
        }

        public void dec(String key) {
            Integer prev = keyCount.getOrDefault(key, 0);
            if (prev == 0) return;
            int next = prev - 1;
            if (next == 0) {
                keyCount.remove(key);
            } else {
                keyCount.put(key, next);
            }
            moveKey(key, prev, next);
        }

        public String getMaxKey() {
            if (freqMap.isEmpty()) return "";
            return freqMap.lastEntry().getValue().iterator().next();
        }

        public String getMinKey() {
            if (freqMap.isEmpty()) return "";
            return freqMap.firstEntry().getValue().iterator().next();
        }
    }

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
}
