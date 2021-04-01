/**
 * https://leetcode.com/problems/coin-change/
 */
class Solution_322 {
    public int coinChange(int[] coins, int amount) {
        int m = coins.length;
        int[][] dp = new int[m + 1][amount + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= amount; j++) {
                int coin = coins[i - 1];
                if (j - coin >= 0 && dp[i][j - coin] != Integer.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - coin] + 1);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return (dp[m][amount] == Integer.MAX_VALUE) ? -1 : dp[m][amount];
    }

    //TODO do it in one-d array
}