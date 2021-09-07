import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     *
     */
}

