/**
 * https://leetcode.com/problems/minimum-path-sum/
 */
class Solution_64 {

    public int _minPathSum(int[][] grid, int m, int n, int[][] dp) {
        if (m == 0 || n == 0) {
            dp[m][n] = Integer.MAX_VALUE;
            return dp[m][n];
        }
        if (m == 1 && n == 1) {
            dp[m][n] = grid[0][0];
            return dp[m][n];
        }
        int left = (dp[m][n - 1] >= 0) ? dp[m][n - 1] : _minPathSum(grid, m, n - 1, dp);
        int up = (dp[m - 1][n] >= 0) ? dp[m - 1][n] : _minPathSum(grid, m - 1, n, dp);
        dp[m][n] = grid[m - 1][n - 1] + Math.min(up, left);
        return dp[m][n];
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = -1;
            }
        }
        return _minPathSum(grid, m, n, dp);
    }

    /**
     * iterative approach
     */
    public int minPathSum3(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }


    /**
     * Space optimisation
     */
    public int minPathSum4(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            dp[0] = dp[0] + grid[i][0];
            for (int j = 1; j < n; j++) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
            }
        }
        return dp[n - 1];
    }
}
