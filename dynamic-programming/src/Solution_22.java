import java.util.*;

/**
 * https://leetcode.com/problems/generate-parentheses/
 */
public class Solution_22 {

    /**
     * Brute force approach
     */
    private Set<String> _generateParenthesis(int n) {
        Set<String> result = new HashSet<>();
        if (n == 1) {
            result.add("()");
            return result;
        }
        for (int i = 1; i < n; i++) {
            Set<String> part1 = _generateParenthesis(i);
            Set<String> part2 = _generateParenthesis(n - i);
            for (String p1 : part1) {
                for (String p2 : part2) {
                    result.add(p1 + p2);
                }
            }
        }
        Set<String> part = _generateParenthesis(n - 1);
        for (String p : part) {
            result.add("(" + p + ")");
        }
        return result;
    }

    public List<String> generateParenthesis(int n) {
        return new ArrayList<>(_generateParenthesis(n));
    }

    /**
     * Dynamic programming
     */
    private Map<Integer, List<String>> cache;

    private List<String> _generateParenthesis2(int n) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        List<String> result = new ArrayList<>();
        if (n == 0) {
            result.add("");
            cache.put(0, result);
            return result;
        }
        for (int i = 0; i < n; i++) {
            List<String> part1 = _generateParenthesis2(i);
            List<String> part2 = _generateParenthesis2(n - i - 1);
            for (String p1 : part1) {
                for (String p2 : part2) {
                    result.add("(" + p1 + ")" + p2);
                }
            }
        }
        cache.put(n, result);
        return result;
    }

    public List<String> generateParenthesis2(int n) {
        cache = new HashMap<>();
        return new ArrayList<>(_generateParenthesis(n));
    }
}

