/**
 * https://leetcode.com/problems/minimum-path-sum/
 * 
 * Problem: Find the minimum path sum from top-left to bottom-right in a grid.
 * You can only move right or down.
 */
class Solution_64 {

    /**
     * Optimized Memoization : Top down
     * Time Complexity: O(M * N)
     * Space Complexity: O(M * N)
     */
    private int _minPathSum(int[][] grid, int i, int j, int[][] dp) {
        // Base case: out of bounds
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        // Base case: reached starting cell
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        // Return memoized value if already computed
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        // Compute minimum path sum from top and left
        int fromTop = _minPathSum(grid, i - 1, j, dp);
        int fromLeft = _minPathSum(grid, i, j - 1, dp);
        dp[i][j] = grid[i][j] + Math.min(fromTop, fromLeft);
        return dp[i][j];
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        // Initialize with -1 to indicate uncomputed values
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        return _minPathSum(grid, m - 1, n - 1, dp);
    }

    /**
     * Iterative Bottom-Up DP
     * Time Complexity: O(M * N)
     * Space Complexity: O(M * N)
     */
    public int minPathSum2(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        // Fill first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }
        // Fill first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        // Fill remaining cells
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }


    /**
     * Space Optimized DP - Using 1D array
     * Time Complexity: O(M * N)
     * Space Complexity: O(min(M, N)) - uses smaller dimension
     */
    public int minPathSum3(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        
        // Use smaller dimension for space optimization
        if (m < n) {
            // Process row by row
            int[] dp = new int[m];
            dp[0] = grid[0][0];
            for (int i = 1; i < m; i++) {
                dp[i] = dp[i - 1] + grid[i][0];
            }
            for (int j = 1; j < n; j++) {
                dp[0] = dp[0] + grid[0][j];
                for (int i = 1; i < m; i++) {
                    dp[i] = Math.min(dp[i], dp[i - 1]) + grid[i][j];
                }
            }
            return dp[m - 1];
        } else {
            // Process column by column
            int[] dp = new int[n];
            dp[0] = grid[0][0];
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j - 1] + grid[0][j];
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

    /**
     * Space Optimized DP - Simple version (always uses column size)
     * Time Complexity: O(M * N)
     * Space Complexity: O(N)
     */
    public int minPathSum4(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        // Initialize first row
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }
        // Process remaining rows
        for (int i = 1; i < m; i++) {
            dp[0] = dp[0] + grid[i][0]; // Update first column
            for (int j = 1; j < n; j++) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
            }
        }
        return dp[n - 1];
    }

    /**
     * In-Place DP - Modifies input grid (use with caution)
     * Time Complexity: O(M * N)
     * Space Complexity: O(1) - no extra space (modifies input)
     */
    public int minPathSum5(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        
        // Fill first row
        for (int j = 1; j < n; j++) {
            grid[0][j] += grid[0][j - 1];
        }
        // Fill first column
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        // Fill remaining cells
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[m - 1][n - 1];
    }

    /**
     * Alternative: BFS/Dijkstra-like approach (different idea)
     * Uses priority queue to explore paths in order of minimum cost
     * Time Complexity: O(M * N * log(M * N)) - slower than DP
     * Space Complexity: O(M * N)
     * Note: This is conceptually different but less efficient than DP
     */
    public int minPathSum6(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        
        // Priority queue: [row, col, cost]
        java.util.PriorityQueue<int[]> pq = new java.util.PriorityQueue<>(
            (a, b) -> Integer.compare(a[2], b[2])
        );
        boolean[][] visited = new boolean[m][n];
        
        pq.offer(new int[]{0, 0, grid[0][0]});
        visited[0][0] = true;
        
        int[][] directions = {{0, 1}, {1, 0}}; // right, down
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int i = curr[0], j = curr[1], cost = curr[2];
            
            // Reached destination
            if (i == m - 1 && j == n - 1) {
                return cost;
            }
            
            // Explore neighbors
            for (int[] dir : directions) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                
                if (ni >= 0 && ni < m && nj >= 0 && nj < n && !visited[ni][nj]) {
                    visited[ni][nj] = true;
                    pq.offer(new int[]{ni, nj, cost + grid[ni][nj]});
                }
            }
        }
        
        return -1; // Should never reach here
    }

    /**
     * Alternative: Recursive without memoization (for comparison - inefficient)
     * Time Complexity: O(2^(M+N)) - exponential
     * Space Complexity: O(M + N) - recursion stack
     */
    public int minPathSumRecursive(int[][] grid) {
        return minPathSumRecursiveHelper(grid, grid.length - 1, grid[0].length - 1);
    }
    
    private int minPathSumRecursiveHelper(int[][] grid, int i, int j) {
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        return grid[i][j] + Math.min(
            minPathSumRecursiveHelper(grid, i - 1, j),
            minPathSumRecursiveHelper(grid, i, j - 1)
        );
    }
}
