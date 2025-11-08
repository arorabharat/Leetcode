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

    static class Approach_3 {
        // longest common subsequence
        private static boolean match(char a, char b) {
            return a == b;
        }

        public static void main(String[] args) {
            Approach_3 approach3 = new Approach_3();
            int answer = approach3.longestCommonSubsequence("mhunuzqrkzsnidwbun", "szulspmhwpazoxijwbq");
            int answer1 = approach3.longestCommonSubsequence1("mhunuzqrkzsnidwbun", "szulspmhwpazoxijwbq");
            System.out.println(answer);
            System.out.println(answer1);
        }

        // f(m,n) = (match(m,n)) ? 1+f(m-1,n-1) : max( f(m-1,n), f(m,n-1) )
        public int _longestCommonSubsequence(String text1, String text2, int m, int n) {
            if (m < 0 || n < 0) {
                return 0;
            }
            if (match(text1.charAt(m), text2.charAt(n))) {
                return 1 + _longestCommonSubsequence(text1, text2, m - 1, n - 1);
            } else {
                return Math.max(_longestCommonSubsequence(text1, text2, m, n - 1), _longestCommonSubsequence(text1, text2, m - 1, n));
            }
        }

        public int longestCommonSubsequence(String text1, String text2) {
            return _longestCommonSubsequence(text1, text2, text1.length() - 1, text2.length() - 2);
        }

        public int longestCommonSubsequence1(String text1, String text2) {
            int m = text1.length(); // abcde
            int n = text2.length(); // ace
            int[][] dp = new int[m + 1][n + 1];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (match(text1.charAt(i), text2.charAt(j))) {
                        dp[i + 1][j + 1] = dp[i][j] + 1;
                    } else {
                        dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                    }
                }
            }
            return dp[m][n];
        }
    }


}