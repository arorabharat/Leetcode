/**
 * https://leetcode.com/problems/decode-ways-ii/
 * TODO : Difficult enough to give
 */
class Solution_639 {


    class Solution1 {

        static long MODULO = 1000000007;

        private void setDp(long[] dp, int i) {
            dp[i] = dp[i] % MODULO;
        }

        public int numDecodings(String s) {
            char[] c = s.toCharArray();
            int n = s.length();
            long[] dp = new long[n + 2];
            dp[n] = 1;
            dp[n + 1] = 0;
            for (int i = n - 1; i >= 0; i--) {
                if (c[i] == '0') {
                    dp[i] = 0;
                } else if (c[i] == '*' && i + 1 < n && c[i + 1] == '*') {
                    dp[i] = 9 * dp[i + 1] + 9 * dp[i + 2] + 6 * dp[i + 2];
                } else if (c[i] == '*' && i + 1 < n && c[i + 1] != '*') {
                    dp[i] = 9 * dp[i + 1] + dp[i + 2];
                    dp[i] = (c[i + 1] - '0' <= 6) ? dp[i] + dp[i + 2] : dp[i];
                } else if (c[i] == '*') {
                    dp[i] = 9 * dp[i + 1];
                } else {
                    dp[i] = dp[i + 1];
                    if (c[i] == '1') {
                        if (i + 1 < n) {
                            if (c[i + 1] == '*') {
                                dp[i] = dp[i] + 9 * dp[i + 2];
                            } else {
                                dp[i] = dp[i] + dp[i + 2];
                            }
                        }
                    } else if (c[i] == '2') {
                        if (i + 1 < n) {
                            if (c[i + 1] == '*') {
                                dp[i] = dp[i] + 6 * dp[i + 2];
                            } else if ((c[i + 1] - '0' <= 6)) {
                                dp[i] = dp[i] + dp[i + 2];
                            }
                        }
                    }
                }
                setDp(dp, i);
            }
            return (int) dp[0];
        }
    }

    class Solution2 {

        private static final int MOD = 1_000_000_007;

        public int numDecodings(String s) {
            int n = s.length();
            // dp0 = dp[i-2], dp1 = dp[i-1]
            long dp0 = 1;
            long dp1 = waysSingle(s.charAt(0));

            for (int i = 1; i < n; i++) {
                char prev = s.charAt(i - 1), cur = s.charAt(i);
                // 1) single-character decodes ending at i
                long dp2 = dp1 * waysSingle(cur) % MOD;
                // 2) two-character decodes ending at i
                dp2 = (dp2 + dp0 * waysPair(prev, cur)) % MOD;

                // roll forward
                dp0 = dp1;
                dp1 = dp2;
            }
            return (int) dp1;
        }

        // Number of ways to decode a single character:
        // '*' -> '1'..'9' => 9 ways; '0' -> 0 ways; else 1 way.
        private int waysSingle(char c) {
            if (c == '*') return 9;
            if (c == '0') return 0;
            return 1;
        }

        // Number of ways to decode a pair (c1,c2):
        // - "**" -> 11..19 (9) + 21..26 (6) = 15
        // - "*d" where d ∈ '0'..'6' -> {1d,2d} = 2; d ∈ '7'..'9' -> {1d} = 1
        // - "d*" where d=='1' -> 9; d=='2' -> 6; else 0
        // - "ab" both digits -> valid if 10 ≤ ab ≤ 26
        private int waysPair(char c1, char c2) {
            if (c1 == '*' && c2 == '*') {
                return 15;
            } else if (c1 == '*') {
                // c2 is digit
                return (c2 <= '6') ? 2 : 1;
            } else if (c2 == '*') {
                // c1 is digit
                if (c1 == '1') return 9;
                if (c1 == '2') return 6;
                return 0;
            } else {
                // both are digits
                int val = (c1 - '0') * 10 + (c2 - '0');
                return (val >= 10 && val <= 26) ? 1 : 0;
            }
        }
    }

    class Solution3 {

        Integer[] dp;

        public int _numDecodings(char[] c, int n) {
            if (n < 0) return Integer.MIN_VALUE;
            if (dp[n] != null) {
                return dp[n];
            }
            if (n == 0) {
                if (c[n] == '0') {
                    return Integer.MIN_VALUE;
                } else if (c[n] == '*') {
                    return 9;
                } else {
                    return 1;
                }
            }
            if (c[n] == '*') {
                int ans = 0;
                int way1 = _numDecodings(c, n - 1);
                if (way1 != Integer.MIN_VALUE) {
                    ans = ans + way1 * 9;
                }
                if (n - 2 >= 0) {
                    int way2 = _numDecodings(c, n - 2);
                    if (way2 != Integer.MIN_VALUE) {
                        ans = ans + way1 * 9;
                    }
                }
                dp[n] = ans;
                return ans;
            }
            return _numDecodings(c, n - 1) + _numDecodings(c, n - 2);
        }

        public int numDecodings(String s) {
            int n = s.length();
            if (n == 0) return 0;
            dp = new Integer[n];
            _numDecodings(s.toCharArray(), n - 1);
            return 0;
        }
    }
}
