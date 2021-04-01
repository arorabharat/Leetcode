/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 */
class Solution_5 {

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

    // 1 based index
    private int reverseIndex(int len, int index) {
        return len - index + 1;
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
}

