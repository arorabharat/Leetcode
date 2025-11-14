/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 */
class Solution_5 {


    class Approach_6 {

        public String longestPalindrome(String s) {
            if (s == null || s.length() < 1) return s;
            int n = s.length();
            boolean[][] dp = new boolean[n][n];

            int bestStart = 0;
            int bestLen = 1;

            // substrings of length 1 are palindromes
            for (int i = 0; i < n; i++) dp[i][i] = true;

            // consider substrings of length L from 2..n
            for (int L = 2; L <= n; L++) {
                for (int i = 0; i + L - 1 < n; i++) {
                    int j = i + L - 1;
                    if (s.charAt(i) != s.charAt(j)) {
                        dp[i][j] = false;
                    } else {
                        // if length <= 2 then it's palindrome when ends match (e.g., "aa")
                        // otherwise rely on inner substring dp[i+1][j-1]
                        if (L <= 2) dp[i][j] = true;
                        else dp[i][j] = dp[i + 1][j - 1];
                    }

                    if (dp[i][j] && L > bestLen) {
                        bestLen = L;
                        bestStart = i;
                    }
                }
            }

            return s.substring(bestStart, bestStart + bestLen);
        }
    }

    class Approach_1 {

        public String longestPalindrome(String s) {
            if (s == null || s.isEmpty()) {
                return "";
            }

            int n = s.length();
            int ls = 0;
            int le = 0;
            for (int left = 0; left < n; left++) {
                for (int right = left; right < n; right++) {
                    if (isPalindrome(s, left, right) && (right - left) > (le - ls)) {
                        ls = left;
                        le = right;
                    }
                }
            }
            return s.substring(ls, le + 1);
        }

        public boolean isPalindrome(String s, int left, int right) {
            while (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }

    // using common subsequence
    class Approach_2 {

        private int[][] longestCommonSubstringEndingAt(String a, String b) {
            int m = a.length();
            int n = b.length();
            int[][] dp = new int[m + 1][n + 1];
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (a.charAt(i - 1) == b.charAt(j - 1)) {
                        dp[i][j] = 1 + dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
            return dp;
        }

        // 1 based index
        private int startIndex(int endIndex, int len) {
            return endIndex - len + 1;
        }


        public String longestPalindrome(String s) {
            StringBuilder sb = new StringBuilder(s);
            String sr = sb.reverse().toString();
            int[][] dp = longestCommonSubstringEndingAt(s, sr);
            int m = s.length();
            int mi = 1;
            int mj = 1;
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= m; j++) {
                    // expected starting index of palindrome string
                    // actual starting index of palindrome character
                    if (dp[mi][mj] < dp[i][j] && startIndex(i, dp[i][j]) == reverseIndex(m, j)) {
                        mi = i;
                        mj = j;
                    }
                }
            }
            return s.substring(startIndex(mi, dp[mi][mj]) - 1, mi);
        }

        // 1 based index
        private int reverseIndex(int len, int index) {
            return len - index + 1;
        }
    }

    // using DP memorisation
    class Approach_3 {

        private Pair[][] lpCache;

        record Pair(int l, int r) {

            public int getLength() {
                return r - l + 1;
            }
        }

        private Pair longestPalindrome(String s, int l, int r) {
            if (r <= l) {
                return new Pair(l, r);
            } else {
                if (lpCache[l][r] != null) {
                    return lpCache[l][r];
                }
                Pair llp = longestPalindrome(s, l, r - 1);
                Pair rlp = longestPalindrome(s, l + 1, r);
                Pair plp = (rlp.getLength() > llp.getLength()) ? rlp : llp;
                if (s.charAt(l) == s.charAt(r)) {
                    Pair clp = longestPalindrome(s, l + 1, r - 1);
                    if (clp.l() == l + 1 && clp.r() == r - 1) {
                        plp = new Pair(l, r);
                    }
                }
                lpCache[l][r] = plp;
                return plp;
            }
        }

        public String longestPalindrome(String s) {
            if (s == null || s.isEmpty()) return "";
            int n = s.length();
            lpCache = new Pair[n][n];
            Pair longestPalindrome = longestPalindrome(s, 0, n - 1);
            return s.substring(longestPalindrome.l(), longestPalindrome.r() + 1);
        }
    }
}

