/**
 * https://leetcode.com/problems/coin-change/
 */
class Solution_322 {


    /**
     * Recursive solution without
     * Using the Integer to ensure
     */
    private Integer _coinChange(int[] coins, int amount, int n) {
        // base case
        if (amount < 0) {
            return null;
        }
        if (amount == 0) {
            return 0;
        }
        if (n == 0) {
            return null;
        }
        Integer takeLastCoin = _coinChange(coins, amount - coins[n - 1], n);
        Integer notTakeLastCoin = _coinChange(coins, amount, n - 1);
        if (takeLastCoin == null && notTakeLastCoin == null) {
            return null;
        }
        if (takeLastCoin == null) {
            return notTakeLastCoin;
        }
        if (notTakeLastCoin == null) {
            return 1 + takeLastCoin;
        }
        return Math.min(1 + takeLastCoin, notTakeLastCoin);
    }

    public int coinChange(int[] coins, int amount) {
        Integer result = _coinChange(coins, amount, coins.length);
        return (result != null) ? result : -1;
    }

    public int coinChange2(int[] coins, int amount) {
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

    /**
     * Space optimisation
     */
    public int coinChange3(int[] coins, int amount) {
        int m = coins.length;
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= amount; j++) {
                int coin = coins[i - 1];
                if (j - coin >= 0 && dp[j - coin] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - coin] + 1);
                } else {
                    dp[j] = dp[j];
                }
            }
        }
        return (dp[amount] == Integer.MAX_VALUE) ? -1 : dp[amount];
    }
}