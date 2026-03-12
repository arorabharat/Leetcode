import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution_981 {

    class Pair {

        Integer timestamp;
        String value;

        public Pair(Integer timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    class TimeMap {

        private final Map<String, List<Pair>> db;

        public TimeMap() {
            db = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            Pair pair = new Pair(timestamp, value);
            db.computeIfAbsent(key, k -> new ArrayList<>()).add(pair);

        }

        String binarySearch(List<Pair> values, int timestamp) {
            int l = 0;
            int r = values.size() - 1;
            int ans = -1;
            while (l <= r) {
                int m = l + (r - l) / 2;
                if (values.get(m).timestamp <= timestamp) {
                    ans = m;
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            if (ans == -1) {
                return "";
            }
            return values.get(ans).value;
        }

        public String get(String key, int timestamp) {
            if (!db.containsKey(key)) {
                return "";
            }
            return binarySearch(db.get(key), timestamp);
        }
    }

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
}
