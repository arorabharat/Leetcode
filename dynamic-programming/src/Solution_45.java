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
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j < n) {
                    dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
                } else {
                    break;
                }
            }
        }
        return dp[n - 1];
    }
}
