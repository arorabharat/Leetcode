/**
 * https://leetcode.com/problems/longest-common-subsequence/
 */
class Solution_1143 {

    // longest common subsequence
    private static boolean match(char a, char b) {
        return a == b;
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

    public static void main(String[] args) {
        Solution_1143 solution_1143 = new Solution_1143();
        int answer = solution_1143.longestCommonSubsequence("mhunuzqrkzsnidwbun", "szulspmhwpazoxijwbq");
        int answer1 = solution_1143.longestCommonSubsequence1("mhunuzqrkzsnidwbun", "szulspmhwpazoxijwbq");
        System.out.println(answer);
        System.out.println(answer1);
    }
}