/**
 * https://leetcode.com/problems/maximum-length-of-repeated-subarray/
 */
class Solution_718 {

    // longest continuous subset
    private static boolean match(int a, int b) {
        return a == b;
    }

    // f(m,n) = (match(m,n)) ? 1+f(m-1,n-1) : 0
    public void _longestCommonSubStringEndingAt(int[] A, int[] B, int m, int n, int[][] dp) {
        if (m == 0 || n == 0) {
            dp[m][n] = 0;
            return;
        }
        if (dp[m - 1][n - 1] == Integer.MIN_VALUE) {
            _longestCommonSubStringEndingAt(A, B, m - 1, n - 1, dp);
        }
        if (dp[m - 1][n] == Integer.MIN_VALUE) {
            _longestCommonSubStringEndingAt(A, B, m - 1, n, dp);
        }
        if (dp[m][n - 1] == Integer.MIN_VALUE) {
            _longestCommonSubStringEndingAt(A, B, m, n - 1, dp);
        }
        if (match(A[m - 1], B[n - 1])) {
            dp[m][n] = 1 + dp[m - 1][n - 1];
        } else {
            dp[m][n] = 0;
        }
    }

    public int findLength1(int[] A, int[] B) {
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 0; i <= A.length; i++) {
            for (int j = 0; j <= B.length; j++) {
                dp[i][j] = Integer.MIN_VALUE;
            }
        }
        _longestCommonSubStringEndingAt(A, B, A.length, B.length, dp);
        int longestCommonContinuousSubset = Integer.MIN_VALUE;
        for (int i = 0; i <= A.length; i++) {
            for (int j = 0; j <= B.length; j++) {
                longestCommonContinuousSubset = Math.max(longestCommonContinuousSubset, dp[i][j]);
            }
        }
        return longestCommonContinuousSubset;
    }

    // find the longest common substring
    public int findLength(int[] A, int[] B) {

        int n = A.length;
        int m = B.length;
        int[][] dp = new int[n + 1][m + 1];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (match(A[i], B[j])) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                }
                ans = Math.max(ans, dp[i + 1][j + 1]);
            }
        }
        return ans;
    }

}
