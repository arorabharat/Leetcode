/**
 * https://leetcode.com/problems/coin-change/
 */
class Solution_322 {


    class Approach_1 {

        private int _coinChange(int[] coins, int n, int amount) {
            if (n < 0) {
                return Integer.MAX_VALUE;
            }
            if (amount < 0) {
                return Integer.MAX_VALUE;
            } else if (amount == 0) {
                return 0;
            } else {
                int c1 = _coinChange(coins, n - 1, amount); // exclude
                int c2 = _coinChange(coins, n, amount - coins[n]);
                if (c2 == Integer.MAX_VALUE) {
                    return c1;
                } else {
                    return Math.min(c1, c2 + 1);
                }
            }
        }

        public int coinChange(int[] coins, int amount) {
            if (coins.length < 1) return 0;
            int res = _coinChange(coins, coins.length - 1, amount);
            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    // bottom up approach of dynamic programming
    class Approach_2 {

        public int coinChange(int[] coins, int amount) {
            int n = coins.length;
            int[][] dp = new int[n + 1][amount + 1];
            for (int i = 1; i <= amount; i++) {
                dp[0][i] = Integer.MAX_VALUE;
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= amount; j++) {
                    int val = coins[i - 1];
                    if (j == val) {
                        dp[i][j] = 1;
                    } else if (j < val) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        dp[i][j] = (dp[i][j - val] == Integer.MAX_VALUE) ? dp[i - 1][j] : Math.min(dp[i - 1][j], dp[i][j - val] + 1);
                    }
                }
            }
            return dp[n][amount] == Integer.MAX_VALUE ? -1 : dp[n][amount];
        }
    }

    class Approach_3 {
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
    }


    class Approach_4 {

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
    }

    // best approach and best implementation
    class Approach_5 {

        /**
         * Space optimisation
         */
        public int coinChange(int[] coins, int amount) {
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
                    }
                }
            }
            return (dp[amount] == Integer.MAX_VALUE) ? -1 : dp[amount];
        }
    }
}