import java.util.*;

class Solution_943 {

    Map<String, String> cache;

    private String merge(String first, String second) {
        int na = first.length();
        int nb = second.length();
        for (int i = 0; i < na; i++) {
            String suffixOfFirst = first.substring(i);
            int suffixLen = na - i;
            String prefixOfSecond = second.substring(0, Math.min(suffixLen, nb));
            if (suffixOfFirst.equals(prefixOfSecond)) {
                return first + second.substring(suffixLen);
            }
        }
        return first + second;
    }

    private String getHash(List<String> words) {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append("-");
            sb.append(word);
        }
        return sb.toString();
    }

    private String permutation(List<String> words) {
        if (words.isEmpty()) {
            return "";
        }
        String minimumSubstring = null;
        for (int i = 0; i < words.size(); i++) {
            String firstWord = words.get(i);
            List<String> leftSubList = words.subList(0, i);
            List<String> rightSubList = words.subList(i + 1, words.size());
            List<String> rest = new ArrayList<>();
            rest.addAll(leftSubList);
            rest.addAll(rightSubList);
            String hashKey = getHash(rest);
            if (!cache.containsKey(hashKey)) {
                cache.put(hashKey, permutation(rest));
            }
            String ansForGivenPermutation = merge(firstWord, cache.get(hashKey));
            if (minimumSubstring == null || minimumSubstring.length() > ansForGivenPermutation.length()) {
                minimumSubstring = ansForGivenPermutation;
            }
        }
        return minimumSubstring;
    }

    public String shortestSuperstring(String[] words) {
        cache = new HashMap<>();
        return permutation(Arrays.asList(words));
    }
}