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
                    count.put(key, count.get(key) - 1);
                } else {
                    count.remove(key);
                }
                updateTreeMap(sortedKeys, prev, prev - 1, key);
            }

        }

        public String getMaxKey() {
            return count.isEmpty() ? "" : sortedKeys.lastEntry().getValue().stream().findFirst().get();
        }

        public String getMinKey() {
            return count.isEmpty() ? "" : sortedKeys.firstEntry().getValue().stream().findFirst().get();
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
