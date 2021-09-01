/**
 * https://leetcode.com/problems/longest-palindromic-subsequence/
 */
class Solution_516 {

    int[][] dp;

    private int _longestPalindromeSubseq(char[] c, int s, int e) {
        if (s == e) return 1;
        if (s > e) return 0;
        if (dp[s][e] != -1) {
            return dp[s][e];
        }
        int case1 = _longestPalindromeSubseq(c, s, e - 1);
        int t = s;
        while (t < e) {
            if (c[t] == c[e]) {
                break;
            }
            t++;
        }
        int case2 = (t == e) ? 1 : _longestPalindromeSubseq(c, t + 1, e - 1) + 2;
        dp[s][e] = Math.max(case1, case2);
        return dp[s][e];
    }

    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        return _longestPalindromeSubseq(s.toCharArray(), 0, n - 1);
    }
}