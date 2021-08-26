import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode.com/problems/valid-parenthesis-string/
 */
class Solution_678 {

    /**
     * Recursive solution
     */
    private boolean _checkValidString(char[] c, int n, int count) {
        if (n < 0) {
            return count == n;
        }
        if (count > 0) {
            return false;
        }
        if (c[n] == '(') {
            return _checkValidString(c, n - 1, count + 1);
        } else if (c[n] == ')') {
            return _checkValidString(c, n - 1, count - 1);
        } else {
            return _checkValidString(c, n - 1, count + 1) || _checkValidString(c, n - 1, count - 1) || _checkValidString(c, n - 1, count);
        }
    }

    public boolean checkValidString(String s) {
        int n = s.length();
        return _checkValidString(s.toCharArray(), n - 1, 0);
    }

    public boolean checkValidString2(String s) {
        int n = s.length();
        char[] c = s.toCharArray();
        List<Set<Integer>> dp = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            dp.add(new HashSet<>());
        }
        dp.get(0).add(0);
        for (int i = 1; i <= n; i++) {
            if (c[i - 1] == '(') {
                for (Integer count : dp.get(i - 1)) {
                    dp.get(i).add(count + 1);
                }
            } else if (c[i - 1] == ')') {
                for (Integer count : dp.get(i - 1)) {
                    if (count > 0) {
                        dp.get(i).add(count - 1);
                    }
                }
            } else {
                for (Integer count : dp.get(i - 1)) {
                    if (count > 0) {
                        dp.get(i).add(count - 1);
                    }
                    dp.get(i).add(count);
                    dp.get(i).add(count + 1);
                }
            }
        }
        return dp.get(n).contains(0);
    }
}