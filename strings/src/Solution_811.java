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


    class Approach_2 {

        private List<String> getSubDomains(String domain) {
            List<String> subDomains = new ArrayList<>();
            for (int i = domain.length() - 1; i >= 0; i--) {
                if (domain.charAt(i) == '.') {
                    subDomains.add(domain.substring(i + 1));
                }
            }
            subDomains.add(domain);
            return subDomains;
        }

        public List<String> subdomainVisits(String[] cpdomains) {
            Map<String, Integer> domainCount = new HashMap<>();
            for (String domainString : cpdomains) {
                String domain = domainString.split(" ")[1];
                Integer domainVistCount = Integer.parseInt(domainString.split(" ")[0]);
                List<String> subDomainList = getSubDomains(domain);
                for (String subDomain : subDomainList) {
                    domainCount.put(subDomain, domainCount.getOrDefault(subDomain, 0) + domainVistCount);
                }
            }
            List<String> result = new ArrayList<>();
            System.out.println(domainCount);
            for (var entrySet : domainCount.entrySet()) {
                String domain = entrySet.getKey();
                Integer count = entrySet.getValue();
                result.add(count + " " + domain);
            }
            return result;
        }
    }
}


