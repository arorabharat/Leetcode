import java.util.Arrays;

/**
 * https://leetcode.com/problems/jump-game/
 */
class Solution_55 {

    /**
     * At every index, we will iterate through the next j cells, where j is the number of steps we can jump from that index
     * mark all these indexes true.
     */
    public boolean canJump(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n];
        Arrays.fill(dp, false);
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j < n) {
                    dp[i + j] = dp[i];
                } else {
                    break;
                }
            }
        }
        return dp[n - 1];
    }
}
