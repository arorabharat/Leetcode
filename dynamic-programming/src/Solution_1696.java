/**
 * https://leetcode.com/problems/jump-game-vi/
 */
class Solution_1696 {

    /**
     * Time Complexity :  O( N^2 )
     * Space Complexity :  O( N )
     */
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        if (n == 0) return 0;
        for (int i = 0; i < n; i++) {
            dp[i] = Integer.MIN_VALUE;
        }
        dp[0] = nums[0];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k && j + i < n; j++) {
                if (dp[i] != Integer.MIN_VALUE) {
                    dp[i + j] = Math.max(dp[i + j], dp[i] + nums[i + j]);
                }
            }
        }
        return dp[n - 1];
    }
}