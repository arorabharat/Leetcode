/**
 * https://leetcode.com/problems/longest-common-subsequence/
 */
class Solution_1143 {

    // recursive approach without optimisation
    class Approach_1 {

        public int longestCommonSubsequence(String text1, String text2, int it1, int it2) {
            if (it1 < 0 || it2 < 0) return 0;
            int c1 = longestCommonSubsequence(text1, text2, it1 - 1, it2);
            int c2 = longestCommonSubsequence(text1, text2, it1, it2 - 1);
            int c3 = 0;
            if (text1.charAt(it1) == text2.charAt(it2)) {
                c3 = 1 + longestCommonSubsequence(text1, text2, it1 - 1, it2 - 1);
            }
            return Math.max(c1, Math.max(c2, c3));
        }

        public int longestCommonSubsequence(String text1, String text2) {
            return longestCommonSubsequence(text1, text2, text1.length() - 1, text2.length() - 1);
        }
    }

    // bottom up dynamic programming
    class Approach_2 {

        private int[][] dp;

        public int longestCommonSubsequence(String text1, String text2, int it1, int it2) {
            if (it1 < 0 || it2 < 0) return 0;
            if (dp[it1][it2] != -1) {
                return dp[it1][it2];
            }
            int c1 = longestCommonSubsequence(text1, text2, it1 - 1, it2);
            int c2 = longestCommonSubsequence(text1, text2, it1, it2 - 1);
            int c3 = 0;
            if (text1.charAt(it1) == text2.charAt(it2)) {
                c3 = 1 + longestCommonSubsequence(text1, text2, it1 - 1, it2 - 1);
            }
            int maxLen = Math.max(c1, Math.max(c2, c3));
            dp[it1][it2] = maxLen;
            return maxLen;
        }

        public int longestCommonSubsequence(String text1, String text2) {

            int n1 = text1.length();
            int n2 = text2.length();
            dp = new int[n1][n2];
            for (int i = 0; i < n1; i++) {
                for (int j = 0; j < n2; j++) {
                    dp[i][j] = -1;
                }
            }
            return longestCommonSubsequence(text1, text2, n1 - 1, n2 - 1);
        }
    }


    // bottom up dynamic prgramming
    class Approach_4 {

        public int longestCommonSubsequence(String text1, String text2) {
            int n1 = text1.length();
            int n2 = text2.length();
            int[][] dp = new int[n1 + 1][n2 + 1];
            for (int i = 1; i <= n1; i++) {
                for (int j = 1; j <= n2; j++) {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                    }
                }
            }
            return dp[n1][n2];
        }
    }

}