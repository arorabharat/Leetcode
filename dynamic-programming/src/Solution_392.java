public class Solution_392 {

    // dynamic programming
    public boolean isSubsequence(String s, String t) {
        int n = s.length();
        int m = t.length();
        boolean[][] dp = new boolean[n + 1][m + 1];
        for (int i = 0; i <= m; i++) {
            dp[0][i] = true;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    dp[i + 1][j + 1] = dp[i + 1][j];
                }
            }
        }
        return dp[n][m];
    }

    // two pointer
    public boolean isSubsequence2(String s, String t) {
        int n = s.length();
        int m = t.length();
        int i = 0;
        int j = 0;
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == s.length();
    }
}
