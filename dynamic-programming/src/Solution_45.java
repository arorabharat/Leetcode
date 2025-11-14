import java.util.Arrays;

/**
 * https://leetcode.com/problems/jump-game-ii/
 */
class Solution_45 {

    /**
     * Approach 1: Optimized Dynamic Programming (Bottom-up)
     * Time Complexity: O(n^2), Space Complexity: O(n)
     * 
     * At every index, we will iterate through the next j cells, where j is the number of steps we can jump from that index
     * Number of steps that would take to reach the next cell = number of jumps to reach the index i plus 1
     * we will take minimum of all such jumps
     * 
     * Optimization: Early termination if we've already reached the end
     */
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int infinity = Integer.MAX_VALUE;
        Arrays.fill(dp, infinity);
        dp[0] = 0;
        
        for (int i = 0; i < n; i++) {
            if (dp[i] == infinity) continue;
            // Early termination: if we can reach the end from current position
            if (i + nums[i] >= n - 1) {
                return dp[i] + 1;
            }
            // Optimize: only update positions that haven't been reached yet or can be reached with fewer jumps
            for (int j = i + 1; j < n && j <= i + nums[i]; j++) {
                if (dp[j] > dp[i] + 1) {
                    dp[j] = dp[i] + 1;
                }
            }
        }
        return dp[n - 1];
    }

    /**
     * Approach 2: Recursive with Memoization (Top-down)
     * Time Complexity: O(n^2), Space Complexity: O(n) for memoization + O(n) for recursion stack
     * 
     * Recursively try all possible jumps from each position and memoize results
     */
    public int jumpRecursive(int[] nums) {
        int n = nums.length;
        int[] memo = new int[n];
        Arrays.fill(memo, -1);
        return jumpRecursiveHelper(nums, 0, memo);
    }

    private int jumpRecursiveHelper(int[] nums, int index, int[] memo) {
        int n = nums.length;
        
        // Base case: reached the end
        if (index >= n - 1) {
            return 0;
        }
        
        // If already computed, return memoized result
        if (memo[index] != -1) {
            return memo[index];
        }
        
        int minJumps = Integer.MAX_VALUE;
        
        // Try all possible jumps from current position
        for (int i = 1; i <= nums[index] && index + i < n; i++) {
            int jumps = jumpRecursiveHelper(nums, index + i, memo);
            if (jumps != Integer.MAX_VALUE) {
                minJumps = Math.min(minJumps, 1 + jumps);
            }
        }
        
        memo[index] = minJumps;
        return minJumps;
    }

    /**
     * Approach 3: Greedy BFS-like Approach (Most Optimal)
     * Time Complexity: O(n), Space Complexity: O(1)
     * 
     * Idea: Think of it as BFS where each level represents one jump.
     * We track:
     * - currentEnd: the farthest position we can reach with current number of jumps
     * - farthest: the farthest position we can reach with one more jump
     * 
     * When we reach currentEnd, we've exhausted all positions reachable with current jumps,
     * so we need one more jump and update currentEnd to farthest.
     */
    public int jumpGreedy(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;
        
        int jumps = 0;
        int currentEnd = 0;  // Farthest position reachable with current number of jumps
        int farthest = 0;    // Farthest position reachable with one more jump
        
        for (int i = 0; i < n - 1; i++) {
            // Update the farthest position we can reach
            farthest = Math.max(farthest, i + nums[i]);
            
            // If we've reached the end of current jump level
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                
                // Early termination: if we can already reach the end
                if (currentEnd >= n - 1) {
                    break;
                }
            }
        }
        
        return jumps;
    }

    /**
     * Approach 4: Greedy - Track Jump Boundaries
     * Time Complexity: O(n), Space Complexity: O(1)
     * 
     * Similar to BFS approach but with slightly different implementation.
     * We track the next jump boundary and update jumps when we cross it.
     */
    public int jumpGreedy2(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;
        
        int jumps = 0;
        int nextJumpEnd = 0;  // The boundary for next jump
        int maxReach = 0;     // Maximum position we can reach
        
        for (int i = 0; i < n - 1; i++) {
            maxReach = Math.max(maxReach, i + nums[i]);
            
            // When we reach the boundary, we need another jump
            if (i == nextJumpEnd) {
                jumps++;
                nextJumpEnd = maxReach;
                
                // Early termination
                if (nextJumpEnd >= n - 1) {
                    break;
                }
            }
        }
        
        return jumps;
    }
}
