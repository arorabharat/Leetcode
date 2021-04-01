/**
 * https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/
 */
class Solution_801 {

    public int minSwap(int[] A, int[] B) {
        int n = A.length;
        int[][] dp = new int[2][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = Integer.MAX_VALUE;
            dp[1][i] = Integer.MAX_VALUE;
        }
        dp[0][0] = 0;
        dp[1][0] = 1;
        for (int i = 1; i < n; i++) {
            if (A[i] > A[i - 1] && B[i] > B[i - 1]) {
                dp[0][i] = Math.min(dp[0][i], dp[0][i - 1]);
                dp[1][i] = Math.min(dp[1][i], dp[1][i - 1] + 1);
            }
            if (A[i] > B[i - 1] && B[i] > A[i - 1]) {
                dp[0][i] = Math.min(dp[0][i], dp[1][i - 1]);
                dp[1][i] = Math.min(dp[1][i], dp[0][i - 1] + 1);
            }
        }
        return Math.min(dp[0][n - 1], dp[1][n - 1]);
    }
}