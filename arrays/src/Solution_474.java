/**
 * https://leetcode.com/problems/ones-and-zeroes/
 * 
 */
class Solution_474 {


    private int countOne(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            count = (c == '1') ? count + 1 : count;
        }
        return count;
    }

    int[][][] dp;

    public int _findMaxForm(String[] strs, int m, int n, int e) {
        if (m < 0 || n < 0) {
            return Integer.MIN_VALUE;
        }
        if (m == 0 && n == 0) {
            return 0;
        }
        if (e < 0) return 0;
        int ones = countOne(strs[e]);
        int zeros = strs[e].length() - ones;
        if (dp[m][n][e] != Integer.MIN_VALUE) {
            return dp[m][n][e];
        }
        dp[m][n][e] = Math.max(_findMaxForm(strs, m - zeros, n - ones, e - 1) + 1,
                _findMaxForm(strs, m, n, e - 1));
        return dp[m][n][e];
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        dp = new int[m + 1][n + 1][len + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= len; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }
        dp[0][0][0] = 0;
        _findMaxForm(strs, m, n, len - 1);
        return dp[m][n][len - 1];
    }
}