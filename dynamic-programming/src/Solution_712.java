/**
 * https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
 */
class Solution_712 {

    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
        }

        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);
        }


        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int case1 = (int) s2.charAt(j - 1) + dp[i][j - 1];
                    int case2 = (int) s1.charAt(i - 1) + dp[i - 1][j];
                    dp[i][j] = Math.min(case1, case2);
                }
            }
        }
        return dp[m][n];
    }
}