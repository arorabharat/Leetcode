/**
 * https://leetcode.com/problems/decode-ways-ii/
 * TODO : Difficult enough to give
 */
class Solution_639 {

    Integer[] dp;

    public int _numDecodings(char c[], int n) {
        if (n < 0) return Integer.MIN_VALUE;
        if (dp[n] != null) {
            return dp[n];
        }
        if (n == 0) {
            if (c[n] == '0') {
                return Integer.MIN_VALUE;
            } else if (c[n] == '*') {
                return 9;
            } else {
                return 1;
            }
        }
        if (c[n] == '*') {
            int ans = 0;
            int way1 = _numDecodings(c, n - 1);
            if (way1 != Integer.MIN_VALUE) {
                ans = ans + way1 * 9;
            }
            if (n - 2 >= 0) {
                int way2 = _numDecodings(c, n - 2);
                if (way2 != Integer.MIN_VALUE) {
                    ans = ans + way1 * 9;
                }
            }
            dp[n] = ans;
            return ans;
        }
        return _numDecodings(c, n - 1) + _numDecodings(c, n - 2);
    }

    public int numDecodings(String s) {
        int n = s.length();
        if (n == 0) return 0;
        dp = new Integer[n];
        _numDecodings(s.toCharArray(), n - 1);
    }
}
