/**
 * https://leetcode.com/problems/coin-change-2/
 */
class Solution_518 {

    class Approach_1 {

        private int _change(int amount, int[] coins, int n) {
            if (amount < 0 || n < 0) {
                return 0;
            }
            if (amount == 0) {
                return 1;
            }
            return _change(amount - coins[n], coins, n) + _change(amount, coins, n - 1);
        }

        public int change(int amount, int[] coins) {
            int n = coins.length;
            return _change(amount, coins, n - 1);
        }
    }

    class Approach_2 {
        /**
         * using the
         */
        public int change1(int amount, int[] coins) {
            int n = coins.length;
            int[][] dp = new int[amount + 1][n];
            for (int j = 0; j < n; j++) {
                dp[0][j] = 1;
            }
            for (int i = 1; i <= amount; i++) {
                for (int j = 0; j < n; j++) {
                    if (i - coins[j] >= 0) {
                        dp[i][j] = dp[i][j] + dp[i - coins[j]][j];
                    }
                    if (j - 1 >= 0) {
                        dp[i][j] = dp[i][j] + dp[i][j - 1];
                    }
                }
            }
            return dp[amount][n - 1];
        }
    }

    class Approach_3 {

        /**
         * Approach to do space optimisation
         * f(amount,N+1) = f(amount,N) + f(amount - coin[N+1],N+1)
         * assume we already know the answer for amount if there N coin and now lets say one more coin is added. hence total coin are N+1
         * We could calculate the answer for f(amount,N+1) by adding two cases
         * case 1 : we could already make the sum without N+1th coin through f(amount,N) ( cases which do not have any instance of N+1 coin )
         * case 2 : we need to find the number of ways to make amount - coin[N + 1] with N + 1 coin ( cases which should have atleast one instance of N+1th coin )
         * base case : amount with less than coin[N+1] would not be affected by addition of the coin.
         *
         * @see Level#GOOD
         */
        public int change3(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            for (int coin : coins) {
                for (int x = coin; x < amount + 1; ++x) {
                    dp[x] += dp[x - coin];
                }
            }
            return dp[amount];
        }
    }

    class Approach_4 {

        public int change(int amount, int[] coins) {
            return _change(amount, coins, coins.length - 1);
        }

        private int _change(int amount, int[] coins, int n) {
            if (amount < 0) {
                return 0;
            } else if (amount == 0) {
                return 1;
            } else {
                if (n < 0) {
                    return 0;
                }
                return _change(amount - coins[n], coins, n) + _change(amount, coins, n - 1);
            }
        }
    }


    class Approach_5 {
        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1];
            dp[0] = 1;
            // number of ways without next coin
            for (int v : coins) {
                for (int j = 1; j <= amount; j++) {
                    if (j >= v) {
                        dp[j] = dp[j] + dp[j - v];
                    }
                }
            }
            return dp[amount];
        }
    }
}


