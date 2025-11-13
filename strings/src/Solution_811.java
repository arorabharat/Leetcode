import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution_811 {

    class Approach_1 {

        private static final String SPACE_SEPARATOR = " ";
        private static final String DOT_SEPARATOR = "\\.";
        private final Map<String, Integer> domainCount = new HashMap<>();

        private void addCount(String key, Integer count) {
            if (domainCount.containsKey(key)) {
                domainCount.compute(key, (k, c) -> c + count);
            } else {
                domainCount.put(key, count);
            }
        }

        public List<String> subdomainVisits(String[] cpdomains) {

            for (String str : cpdomains) {
                String[] tokens = str.split(SPACE_SEPARATOR);
                Integer count = Integer.parseInt(tokens[0]);
                String domain = tokens[1];
                String[] domainSplit = domain.split(DOT_SEPARATOR);
                addCount(domain, count);
                if (domainSplit.length == 3) {
                    addCount(domainSplit[1] + "." + domainSplit[2], count);
                    addCount(domainSplit[2], count);
                } else if (domainSplit.length == 2) {
                    addCount(domainSplit[1], count);
                }
            }
            List<String> result = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : domainCount.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                result.add(value + SPACE_SEPARATOR + key);
            }
            return result;
        }
    }
}


