import java.util.Arrays;

/**
 * https://leetcode.com/problems/jump-game/
 */
class Solution_55 {

    /**
     * Approach 1: Optimized DP (O(n^2) time, O(n) space)
     * Only process positions that are reachable, avoiding unnecessary iterations.
     */
    public boolean canJump(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true;
        
        for (int i = 0; i < n; i++) {
            if (!dp[i]) continue; // Skip unreachable positions
            for (int j = 1; j <= nums[i] && i + j < n; j++) {
                dp[i + j] = true;
                if (i + j == n - 1) return true; // Early exit
            }
        }
        return dp[n - 1];
    }

    /**
     * Approach 2: Greedy (O(n) time, O(1) space) - OPTIMAL
     * Track the farthest position we can reach. If we can't reach current index, return false.
     */
    public boolean canJumpGreedy(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) return false; // Can't reach current position
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest >= nums.length - 1) return true; // Early exit
        }
        return true;
    }

    /**
     * Approach 3: Recursive with Memoization (O(n^2) time, O(n) space)
     * Try all possible jumps from each position, memoizing results.
     */
    public boolean canJumpRecursive(int[] nums) {
        int n = nums.length;
        Boolean[] memo = new Boolean[n];
        return canJumpHelper(nums, 0, memo);
    }

    private boolean canJumpHelper(int[] nums, int index, Boolean[] memo) {
        if (index >= nums.length - 1) return true;
        if (memo[index] != null) return memo[index];
        
        int maxJump = nums[index];
        for (int jump = 1; jump <= maxJump; jump++) {
            if (canJumpHelper(nums, index + jump, memo)) {
                memo[index] = true;
                return true;
            }
        }
        
        memo[index] = false;
        return false;
    }

    /**
     * Approach 4: Bottom-up DP Optimization (O(n^2) time, O(n) space)
     * Process from right to left, checking if we can reach the end from each position.
     */
    public boolean canJumpBottomUp(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[n - 1] = true; // Last position is always reachable
        
        for (int i = n - 2; i >= 0; i--) {
            int maxJump = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= maxJump; j++) {
                if (dp[j]) {
                    dp[i] = true;
                    break; // Found a path, no need to check further
                }
            }
        }
        return dp[0];
    }

    /**
     * Approach 5: Greedy from End (O(n) time, O(1) space)
     * Work backwards, tracking the leftmost position that can reach the end.
     */
    public boolean canJumpGreedyBackward(int[] nums) {
        int lastPos = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= lastPos) {
                lastPos = i; // Update the leftmost position that can reach the end
            }
        }
        return lastPos == 0;
    }
}
