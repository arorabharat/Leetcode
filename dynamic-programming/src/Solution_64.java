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
}
