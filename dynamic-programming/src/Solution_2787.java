import java.util.HashMap;
import java.util.Map;

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

    // dynamic programming approach - bottom up
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


    // dynamic programming approach - top down
    class Solution {
        private final Map<String, Integer> memo = new HashMap<>();
        private int n, x;

        public int waysToExpress(int n, int x) {
            this.n = n;
            this.x = x;
            return dfs(1, n);
        }

        // Try numbers starting from 'start' to use or skip
        private int dfs(int start, int remaining) {
            if (remaining == 0) return 1;  // Found a valid way
            if (remaining < 0) return 0;   // Invalid path

            String key = start + "," + remaining;
            if (memo.containsKey(key)) return memo.get(key);

            int ways = 0;
            long power = (long) Math.pow(start, x);
            if (power > remaining) {
                memo.put(key, 0);
                return 0;
            }

            // Include start^x
            ways += dfs(start + 1, (int) (remaining - power));
            int MOD = 1_000_000_007;
            ways %= MOD;

            // Exclude start^x
            ways += dfs(start + 1, remaining);
            ways %= MOD;

            memo.put(key, ways);
            return ways;
        }
    }
}
