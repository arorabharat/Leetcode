/**
 * https://leetcode.com/problems/unique-paths-ii/
 */
class Solution_63 {

    /**
     * Explanation -
     * base case - there is only one possible way to reach top left cell if there is no obstacle
     * recursive step - at every other cell we can either come from left or top
     * recursive step - at every other cell in the first row we can come from left
     * recursive step - at every other cell in the first col we can come from top
     * Time complexity - O(M * N )
     * Space complexity - O(M * N )
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        if (m == 0) return 0;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1];
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * Reducing space complexity.
     * before calculating dp[j] in ith iteration = represent the number of ways to reach jth col in the i-1 iteration
     * after calculating dp[j] in ith iteration = represent the number of ways to reach jth col in the i iteration
     * So, dp[j] = dp[j] + dp[j - 1] ≣ dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
     */
    public int uniquePathsWithObstacles1(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        if (m == 0) return 0;
        int n = obstacleGrid[0].length;
        int[] dp = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                } else if (i == 0 && j == 0) {
                    dp[j] = 1;
                } else if (i == 0) {
                    dp[j] = dp[j - 1];
                } else if (j != 0) {
                    dp[j] = dp[j] + dp[j - 1];
                }
            }
        }
        return dp[n - 1];
    }
}
