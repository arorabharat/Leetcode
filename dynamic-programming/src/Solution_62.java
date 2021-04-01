/**
 * https://leetcode.com/problems/unique-paths/
 */
class Solution_62 {

    /**
     * Explanation -
     * base case - there is only one possible way to reach any cell in first row and first col
     * recursive step - at every other cell we can either come from left or top
     * Time complexity - O(M * N )
     * Space complexity - O(M * N )
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
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
    public int uniquePaths1(int m, int n) {
        int[] dp = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[j] = 1;
                } else {
                    dp[j] = dp[j] + dp[j - 1];
                }
            }
        }
        return dp[n - 1];
    }


    /**
     * Reducing the time complexity.
     * The whole problem can be viewed as pascal triangle if you look closely
     */
    public int uniquePaths2(int m, int n) {
        int[] dp = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[j] = 1;
                } else {
                    dp[j] = dp[j] + dp[j - 1];
                }
            }
        }
        return dp[n - 1];
    }


}
