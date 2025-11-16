import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/unique-binary-search-trees/
 * 
 * Problem: Given n, return the number of structurally unique BSTs that have exactly n nodes
 * with unique values from 1 to n.
 * 
 * This problem follows the Catalan number sequence: C(n) = (2n)! / ((n+1)! * n!)
 */
class Solution_96 {

    /**
     * Approach 1: Top-down DP with Memoization (Optimized)
     * Time: O(n^2), Space: O(n)
     * 
     * Optimizations:
     * - Removed redundant base case check
     * - Early return for invalid input
     * - Cleaner memoization logic
     */
    class Approach_1 {

        private final Map<Integer, Integer> memo = new HashMap<>();

        public int numTrees(int n) {
            if (n < 0) return 0;
            if (n <= 1) return 1; // Base case: 0 or 1 node = 1 unique BST
            
            if (memo.containsKey(n)) {
                return memo.get(n);
            }
            
            int result = 0;
            // For each node i as root: left subtree has i nodes, right has (n-1-i) nodes
            for (int i = 0; i < n; i++) {
                result += numTrees(i) * numTrees(n - 1 - i);
            }
            
            memo.put(n, result);
            return result;
        }
    }

    /**
     * Approach 2: Bottom-up DP (Optimized)
     * Time: O(n^2), Space: O(n)
     * 
     * Optimizations:
     * - Added input validation
     * - More efficient array initialization
     */
    class Approach_2 {

        public int numTrees(int n) {
            if (n < 0) return 0;
            if (n <= 1) return 1;
            
            int[] dp = new int[n + 1];
            dp[0] = 1; // Empty tree
            dp[1] = 1; // Single node
            
            // Build up from smaller subproblems
            for (int i = 2; i <= n; i++) {
                for (int j = 0; j < i; j++) {
                    // j nodes on left, (i-1-j) nodes on right
                    dp[i] += dp[j] * dp[i - 1 - j];
                }
            }
            return dp[n];
        }
    }

    /**
     * Approach 3: Catalan Number Formula (Direct Calculation)
     * Time: O(n), Space: O(1)
     * 
     * Uses the direct formula: C(n) = (2n)! / ((n+1)! * n!)
     * This is mathematically different from DP approaches.
     * 
     * Note: For large n, this might overflow. Use long for intermediate calculations.
     */
    class Approach_3 {

        public int numTrees(int n) {
            if (n < 0) return 0;
            if (n <= 1) return 1;
            
            // C(n) = (2n)! / ((n+1)! * n!)
            // We can compute this as: C(n) = C(2n, n) / (n + 1)
            // Where C(2n, n) is the binomial coefficient
            
            long result = 1;
            // Calculate C(2n, n) / (n + 1)
            // Using: C(2n, n) = (2n)! / (n! * n!)
            // So: C(n) = C(2n, n) / (n + 1)
            for (int i = 0; i < n; i++) {
                result = result * (2L * n - i) / (i + 1);
            }
            result = result / (n + 1);
            
            return (int) result;
        }
    }

    /**
     * Approach 4: Catalan Number Recurrence Relation (Iterative)
     * Time: O(n), Space: O(1)
     * 
     * Uses the recurrence: C(n+1) = 2(2n+1)/(n+2) * C(n)
     * This avoids factorial calculations and is more efficient.
     */
    class Approach_4 {

        public int numTrees(int n) {
            if (n < 0) return 0;
            if (n <= 1) return 1;
            
            long catalan = 1; // C(0) = 1
            
            // Calculate C(n) using recurrence: C(i+1) = 2(2i+1)/(i+2) * C(i)
            for (int i = 0; i < n; i++) {
                catalan = catalan * 2 * (2L * i + 1) / (i + 2);
            }
            
            return (int) catalan;
        }
    }
}