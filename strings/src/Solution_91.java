import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution_91 {


    class Solution1 {

        Map<Integer, Integer> cache = new HashMap<>();

        private int _numDecodings(char[] s, int i, int n) {
            if (i > n) return 0;
            if (i < 0) return 0;
            if (i == n) return 1;
            if (cache.containsKey(i)) return cache.get(i);
            int ans;
            if (s[i] == '0') {
                ans = 0;
            } else {
                int way1 = _numDecodings(s, i + 1, n);
                if (s[i] == '1' && i + 1 < n) {
                    ans = way1 + _numDecodings(s, i + 2, n);
                } else if (s[i] == '2' && i + 1 < n && s[i + 1] <= '6' && s[i + 1] >= '0') {
                    ans = way1 + _numDecodings(s, i + 2, n);
                } else {
                    ans = way1;
                }
            }
            cache.put(i, ans);
            return ans;
        }

        public int numDecodings(String s) {
            return _numDecodings(s.toCharArray(), 0, s.length());
        }
    }

    class Solution2 {

        private static boolean isBetween20To26(char[] c, int i, int n) {
            return c[i] == '2' && i + 1 < n && c[i + 1] <= '6' && c[i + 1] >= '0';
        }

        private static boolean isBetween10To19(char[] c, int i, int n) {
            return c[i] == '1' && i + 1 < n;
        }

        public int numDecodings(String s) {
            char[] c = s.toCharArray();
            int n = s.length();
            int[] dp = new int[n + 2];
            dp[n] = 1;
            dp[n + 1] = 0;
            for (int i = n - 1; i >= 0; i--) {
                if (c[i] == '0') {
                    dp[i] = 0;
                } else {
                    dp[i] = dp[i + 1];
                    if (isBetween10To19(c, i, n) || isBetween20To26(c, i, n)) {
                        dp[i] += dp[i + 2];
                    }
                }
            }
            return dp[0];
        }
    }


    /**
     * second solution
     */

    Map<Integer, Integer> memo = new HashMap<>();
    private int[] cache;

    private int numDecodings(char[] s, int n) {
        if (n == -1) return 1;
        if (cache[n] != Integer.MIN_VALUE) {
            return cache[n];
        }
        if (n == 0 && s[n] == '0') {
            cache[n] = 0;
        } else if (n == 0) {
            cache[n] = 1;
        } else if (s[n] == '0' && (s[n - 1] == '1' || s[n - 1] == '2')) {
            cache[n] = numDecodings(s, n - 2);
        } else if (s[n] == '0') {
            cache[n] = 0;
        } else if (s[n - 1] == '2' && 0 < s[n] - '0' && s[n] - '0' < 7 || s[n - 1] == '1') {
            cache[n] = numDecodings(s, n - 2) + numDecodings(s, n - 1);
        } else {
            cache[n] = numDecodings(s, n - 1);
        }
        return cache[n];
    }

    public int numDecodings(String s) {
        int n = s.length();
        cache = new int[n];
        Arrays.fill(cache, Integer.MIN_VALUE);
        return numDecodings(s.toCharArray(), n - 1);
    }

    public int numDecodings2(String s) {
        return recursiveWithMemo(0, s);
    }

    /**
     * If we have already computed a value then we will pick from the cache
     * Cache store how many string we could make starting from a given index. If we could not make any valid decoding then it's value will be zero
     * For example if string  is 15
     * 0 1 2
     * str = 1 5
     * we could make 1 or 15 then we need find the answer for the remaining string in both the cases
     * We start with a given index and check if we could make a one digit valid decoding or two digit valida decoding
     */
    private int recursiveWithMemo(int index, String str) {
        // Have we already seen this substring?
        if (memo.containsKey(index)) {
            return memo.get(index);
        }
        if (index == str.length()) {
            return 1;
        }
        if (str.charAt(index) == '0') {
            return 0;
        }
        if (index == str.length() - 1) {
            return 1;
        }
        int ans = recursiveWithMemo(index + 1, str);
        if (Integer.parseInt(str.substring(index, index + 2)) <= 26) {
            ans += recursiveWithMemo(index + 2, str);
        }
        memo.put(index, ans);
        return ans;
    }

    /**
     * Iterative approach, second approach could also be converted into iterative approach
     * base case : Ways to decode a string of size 1 is 1. Unless the string is '0'. '0' doesn't have a single digit decode.
     */
    public int numDecodings3(String s) {
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for (int i = 2; i < dp.length; i++) {
            // Check if successful single digit decode is possible.
            if (s.charAt(i - 1) != '0') {
                dp[i] = dp[i - 1];
            }
            // Check if successful two digit decode is possible.
            int twoDigit = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[s.length()];
    }

    /**
     * optimising third further, we just need last two values like Fibonacci series
     */
    public int numDecodings4(String s) {
        int n = s.length();
        int lastSecondIndex = 1;
        int lastIndex = s.charAt(0) == '0' ? 0 : 1;
        for (int i = 2; i <= n; i++) {
            int current = 0;
            if (s.charAt(i - 1) != '0') {
                current = lastIndex;
            }
            int twoDigit = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigit >= 10 && twoDigit <= 26) {
                current += lastSecondIndex;
            }
            lastSecondIndex = lastIndex;
            lastIndex = current;
        }
        return lastIndex;
    }
}