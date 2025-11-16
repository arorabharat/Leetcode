import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/triangle/
 * Problem: Find the minimum path sum from top to bottom of a triangle.
 * 
 * Time Complexity: O(n^2) where n is the number of rows
 * Space Complexity: Varies by approach (O(n^2) to O(1))
 */
class Solution_120 {

    /*
         0 1 2 3 4
       0 ∞ 0 ∞ ∞ ∞
       1 ∞ 2 ∞ ∞ ∞
       2 ∞ 3 4 ∞ ∞
       3 ∞ 6 5 7 ∞
       4 ∞ 4 1 8 3

     */

    /**
     * Approach 1: Bottom-up DP with O(n^2) space
     * Optimized: Removed unnecessary helper methods, simplified logic
     */
    class Approach_1_Optimized {

        public int minimumTotal(List<List<Integer>> triangle) {
            int m = triangle.size();
            if (m == 0) return 0;
            
            int[][] dp = new int[m + 1][m + 1];
            Arrays.fill(dp[0], Integer.MAX_VALUE);
            dp[0][1] = 0; // base case
            
            for (int i = 1; i <= m; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE);
                for (int j = 1; j <= i; j++) {
                    int val = triangle.get(i - 1).get(j - 1);
                    dp[i][j] = val + Math.min(dp[i - 1][j - 1], dp[i - 1][j]);
                }
            }
            
            int min = Integer.MAX_VALUE;
            for (int j = 1; j <= m; j++) {
                min = Math.min(min, dp[m][j]);
            }
            return min;
        }
    }

    /**
     * Approach 2: Bottom-up DP with direct indexing
     * Optimized: Removed unnecessary infinity checks, simplified boundary handling
     */
    class Approach_2_Optimized {

        public int minimumTotal(List<List<Integer>> triangle) {
            int r = triangle.size();
            if (r == 0) return 0;
            
            int[][] dp = new int[r][r];
            dp[0][0] = triangle.get(0).get(0);
            
            for (int i = 1; i < r; i++) {
                List<Integer> row = triangle.get(i);
                // First element can only come from above
                dp[i][0] = dp[i - 1][0] + row.get(0);
                
                // Middle elements can come from above or above-left
                for (int j = 1; j < i; j++) {
                    dp[i][j] = row.get(j) + Math.min(dp[i - 1][j - 1], dp[i - 1][j]);
                }
                
                // Last element can only come from above-left
                dp[i][i] = dp[i - 1][i - 1] + row.get(i);
            }
            
            // Find minimum in the last row
            int minCost = dp[r - 1][0];
            for (int i = 1; i < r; i++) {
                minCost = Math.min(minCost, dp[r - 1][i]);
            }
            return minCost;
        }
    }

    /**
     * Approach 3: Space-optimized DP - O(n) space
     * Key insight: We only need the previous row to compute the current row
     */
    class Approach_3_SpaceOptimized {

        public int minimumTotal(List<List<Integer>> triangle) {
            int r = triangle.size();
            if (r == 0) return 0;
            
            // Only store the previous row
            int[] prev = new int[r];
            prev[0] = triangle.get(0).get(0);
            
            for (int i = 1; i < r; i++) {
                int[] curr = new int[r];
                List<Integer> row = triangle.get(i);
                
                // First element
                curr[0] = prev[0] + row.get(0);
                
                // Middle elements
                for (int j = 1; j < i; j++) {
                    curr[j] = row.get(j) + Math.min(prev[j - 1], prev[j]);
                }
                
                // Last element
                curr[i] = prev[i - 1] + row.get(i);
                
                prev = curr;
            }
            
            // Find minimum in the last row
            int minCost = prev[0];
            for (int i = 1; i < r; i++) {
                minCost = Math.min(minCost, prev[i]);
            }
            return minCost;
        }
    }

    /**
     * Approach 4: Space-optimized DP with single array - O(n) space
     * Process from right to left to avoid overwriting values we still need
     */
    class Approach_4_SingleArray {

        public int minimumTotal(List<List<Integer>> triangle) {
            int r = triangle.size();
            if (r == 0) return 0;
            
            int[] dp = new int[r];
            dp[0] = triangle.get(0).get(0);
            
            for (int i = 1; i < r; i++) {
                List<Integer> row = triangle.get(i);
                // Process from right to left to avoid overwriting
                dp[i] = dp[i - 1] + row.get(i); // Last element
                for (int j = i - 1; j > 0; j--) {
                    dp[j] = row.get(j) + Math.min(dp[j - 1], dp[j]);
                }
                dp[0] = dp[0] + row.get(0); // First element
            }
            
            int minCost = dp[0];
            for (int i = 1; i < r; i++) {
                minCost = Math.min(minCost, dp[i]);
            }
            return minCost;
        }
    }

    /**
     * Approach 5: Top-down memoization (Recursive with DP)
     * Different idea: Start from top and recursively explore paths with memoization
     */
    class Approach_5_TopDownMemoization {

        public int minimumTotal(List<List<Integer>> triangle) {
            int r = triangle.size();
            if (r == 0) return 0;
            
            Integer[][] memo = new Integer[r][r];
            return dfs(triangle, 0, 0, memo);
        }

        private int dfs(List<List<Integer>> triangle, int row, int col, Integer[][] memo) {
            if (row == triangle.size() - 1) {
                return triangle.get(row).get(col);
            }
            
            if (memo[row][col] != null) {
                return memo[row][col];
            }
            
            int current = triangle.get(row).get(col);
            int left = dfs(triangle, row + 1, col, memo);
            int right = dfs(triangle, row + 1, col + 1, memo);
            
            memo[row][col] = current + Math.min(left, right);
            return memo[row][col];
        }
    }

    /**
     * Approach 6: In-place modification (if triangle is mutable)
     * Modify the triangle itself to store minimum path sums
     * Note: This modifies the input, which may not be allowed in all scenarios
     */
    class Approach_6_InPlace {

        public int minimumTotal(List<List<Integer>> triangle) {
            int r = triangle.size();
            if (r == 0) return 0;
            
            // Start from second-to-last row and work upwards
            for (int i = r - 2; i >= 0; i--) {
                List<Integer> currRow = triangle.get(i);
                List<Integer> nextRow = triangle.get(i + 1);
                
                for (int j = 0; j <= i; j++) {
                    int minPath = currRow.get(j) + Math.min(nextRow.get(j), nextRow.get(j + 1));
                    currRow.set(j, minPath);
                }
            }
            
            return triangle.get(0).get(0);
        }
    }

    /**
     * Approach 7: Bottom-up from bottom row (most intuitive)
     * Build solution from bottom to top, finding minimum path to each cell
     */
    class Approach_7_BottomUp {

        public int minimumTotal(List<List<Integer>> triangle) {
            int r = triangle.size();
            if (r == 0) return 0;
            
            // Start from the second-to-last row and move up
            for (int i = r - 2; i >= 0; i--) {
                for (int j = 0; j <= i; j++) {
                    int minBelow = Math.min(
                        triangle.get(i + 1).get(j),
                        triangle.get(i + 1).get(j + 1)
                    );
                    triangle.get(i).set(j, triangle.get(i).get(j) + minBelow);
                }
            }
            
            return triangle.get(0).get(0);
        }
    }
}
