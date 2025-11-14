import java.util.Arrays;

/**
 * https://leetcode.com/problems/jump-game-ii/
 * https://www.geeksforgeeks.org/minimum-number-of-jumps-to-reach-end-of-a-given-array/
 */
class Solution_45 {

    /**
     * At every index, we will iterate through the next j cells, where j is the number of steps we can jump from that index
     * Number of steps that would take to reach the next cell = number of jumps to reach the index i plus 1
     * we will take minimum of all such jumps
     */
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int infinity = Integer.MAX_VALUE;
        Arrays.fill(dp, infinity);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == infinity) continue;
            for (int j = i + 1; j < n && j <= i + nums[i]; j++) {
                dp[j] = Math.min(dp[j], dp[i] + 1);
            }
        }
        return dp[n - 1];
    }
}
