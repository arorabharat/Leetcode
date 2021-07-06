import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-falling-path-sum/
 */
class Solution_931 {

    // same as solution 120
    private int minInArray(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int e : arr) {
            min = Math.min(min, e);
        }
        return min;
    }

    private void initialiseWithInfinity(int[][] arr) {
        for (int[] ints : arr) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
    }

    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length;
        if (m == 0) return 0;
        int n = matrix[0].length;
        int[][] dp = new int[m + 2][n + 2];
        initialiseWithInfinity(dp);
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 0; // base case
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i][j] = matrix[i - 1][j - 1] + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j + 1]));
            }
        }
        return minInArray(dp[m]);
    }
}