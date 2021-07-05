/**
 * https://leetcode.com/problems/combination-sum-iv/
 */
class Solution_377 {
    /**
     * Explanation -
     * This is like coin problem where you have infinite supply of given type of coins and you have to reach a possible sum
     * Case 1 : you pick the current integer for the target, then you have to recursively find the remaining target given all integers
     * Case 2 : you do not pick the the current integer for the target, that means you can no longer pick the integer later as well as
     * you could have picked the integer in the first place, so now you need to find the target all integer except the current integer.
     * Time complexity -
     * Space Complexity -
     */
    public int combinationSum4(int[] nums, int target) {
        int m = nums.length;
        int[][] dp = new int[m + 1][target + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int currTarget = 1; currTarget <= target; currTarget++) {
                for (int j = 1; j <= i; j++) {
                    int remainingTarget = currTarget - nums[j - 1];
                    if (remainingTarget >= 0) {
                        dp[i][currTarget] = dp[i][currTarget] + dp[j][remainingTarget];
                    }
                }
                dp[i][currTarget] = dp[i][currTarget] + dp[i - 1][currTarget];
                System.out.print(dp[i][currTarget] + " ");
            }
            System.out.println();
        }
        return dp[m][target];
    }
}
