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
     * Optimized space complexity - using 1D array.
     * before calculating dp[j] in ith iteration = represent the number of ways to reach jth col in the i-1 iteration
     * after calculating dp[j] in ith iteration = represent the number of ways to reach jth col in the i iteration
     * So, dp[j] = dp[j] + dp[j - 1] ≣ dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
     * Time complexity - O(M * N)
     * Space complexity - O(N)
     */
    public int uniquePaths1(int m, int n) {
        int[] dp = new int[n];
        // Initialize first row - all cells in first row have 1 path
        for (int j = 0; j < n; j++) {
            dp[j] = 1;
        }
        // Process remaining rows
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }
        return dp[n - 1];
    }


    /**
     * Combinatorial/Mathematical approach - Pascal Triangle insight.
     * To reach (m-1, n-1) from (0,0), we need (m-1) down moves and (n-1) right moves.
     * Total moves = (m-1) + (n-1) = m+n-2
     * Number of ways = C(m+n-2, m-1) = C(m+n-2, n-1)
     * We choose min(m-1, n-1) to optimize calculation.
     * Time complexity - O(min(m, n))
     * Space complexity - O(1)
     */
    public int uniquePaths2(int m, int n) {
        // Total moves needed
        int totalMoves = m + n - 2;
        // Choose the smaller value to minimize calculations
        int k = Math.min(m - 1, n - 1);
        
        // Calculate C(totalMoves, k) = C(m+n-2, min(m-1, n-1))
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (totalMoves - k + i) / i;
        }
        return (int) result;
    }

    /**
     * Memoized recursive approach (Top-down DP).
     * Time complexity - O(M * N)
     * Space complexity - O(M * N) for memoization + O(M + N) for recursion stack
     */
    public int uniquePaths3(int m, int n) {
        Integer[][] memo = new Integer[m][n];
        return uniquePathsHelper(0, 0, m, n, memo);
    }

    private int uniquePathsHelper(int i, int j, int m, int n, Integer[][] memo) {
        // Base case: reached destination
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        // Base case: out of bounds
        if (i >= m || j >= n) {
            return 0;
        }
        // Return memoized result if available
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        // Recurse: move right or down
        memo[i][j] = uniquePathsHelper(i + 1, j, m, n, memo) + 
                     uniquePathsHelper(i, j + 1, m, n, memo);
        return memo[i][j];
    }


}
