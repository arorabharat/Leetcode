public class Solution_2787 {

    class Solution1 {

        int x;

        // number of ways to make s if you have n positive numbers and each number could be used once
        // where s can not be negative
        private long _numberOfWays(double s, double n) {
            if (s < 0 || n < 0) {
                return 0;
            }
            // s has become zero and n is not zero, that means we have made our sum and we can leave rest of the numbers
            if (s == 0) {
                return 1;
            }
            // if there are more numbers but s > 0
            if (n == 0) {
                return 0;
            }
            double y = Math.pow(n, this.x);
            long takeN = modulo(_numberOfWays(s - y, n - 1));
            long doNotTakeN = modulo(_numberOfWays(s, n - 1));
            return takeN + doNotTakeN;
        }

        long modulo(long x) {
            return x % 1_000_000_007;
        }

        // where s belongs [1,s]
        // where x belongs x > 0
        // where n1^x + n2^x ... = s
        // ni^x <= s
        public int numberOfWays(int s, int x) {
            if (s <= 0 || x <= 0) return 0;
            this.x = x;
            double N = Math.pow(s, (double) 1 / x);
            return (int) _numberOfWays(s, Math.floor(N) + 1);
        }
    }

    // dynamic programming approach
    class Solution2 {
        long modulo(long x) {
            return x % 1_000_000_007;
        }

        public int numberOfWays(int S, int x) {
            int N = (int) Math.ceil(Math.pow(S, (double) 1 / x));
            long[][] dp = new long[S + 1][N + 1];

            for (int n = 0; n <= N; n++) {
                dp[0][n] = 1;
            }

            for (int s = 1; s <= S; s++) {
                for (int n = 1; n <= N; n++) {
                    double y = Math.pow(n, x);
                    if (s - y >= 0) {
                        dp[s][n] = dp[s - (int) y][n - 1] + dp[s][n - 1];
                    } else {
                        dp[s][n] = dp[s][n - 1];
                    }
                    dp[s][n] = modulo(dp[s][n]);
                }
            }
            return (int) dp[S][N];
        }
    }
}
