import java.util.HashMap;
import java.util.Map;

class Solution_76 {
    /**
     * Time Complexity: O(n), where n is the size of the targetCharFrequencyMap.
     * This is because the stream iterates through all the values in the map once to calculate the sum.
     */
    public boolean isUnmatchCharCountZero(Map<Character, Integer> targetCharFrequencyMap) {
        return targetCharFrequencyMap.values().stream().noneMatch(val -> val > 0);
    }

    public String minWindow(String s, String t) {
        if (t == null) return "";
        if (t.isEmpty()) return "";
        Map<Character, Integer> targetCharFrequencyMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetCharFrequencyMap.put(c, targetCharFrequencyMap.getOrDefault(c, 0) + 1);
        }
        int start = 0;
        int end;
        int N = s.length();
        char[] s_c = s.toCharArray();
        int minSubstringSize = Integer.MAX_VALUE;
        int minSubstringStart = -1;
        int minSubstringEnd = -1;
        while (start < N && !targetCharFrequencyMap.containsKey(s_c[start])) {
            start++;
        }
        end = start;
        while (end < N) {
            if (targetCharFrequencyMap.containsKey(s_c[end])) {
                targetCharFrequencyMap.put(s_c[end], targetCharFrequencyMap.get(s_c[end]) - 1);
            }
            if (isUnmatchCharCountZero(targetCharFrequencyMap)) {
                int tempMinSubstringSize = end - start + 1;
                if (tempMinSubstringSize < minSubstringSize) {
                    minSubstringSize = tempMinSubstringSize;
                    minSubstringStart = start;
                    minSubstringEnd = end;
                }
                do {
                    tempMinSubstringSize = end - start + 1;
                    if (tempMinSubstringSize < minSubstringSize) {
                        minSubstringSize = tempMinSubstringSize;
                        minSubstringStart = start;
                        minSubstringEnd = end;
                    }
                    if (targetCharFrequencyMap.containsKey(s_c[start])) {
                        targetCharFrequencyMap.put(s_c[start], targetCharFrequencyMap.get(s_c[start]) + 1);
                    }
                    start++;
                } while (start < N && isUnmatchCharCountZero(targetCharFrequencyMap));
            }
            end++;
        }
        return (minSubstringSize == Integer.MAX_VALUE) ?
                "" : s.substring(minSubstringStart, minSubstringEnd + 1);
    }
}
