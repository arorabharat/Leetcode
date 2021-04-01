/**
 * https://leetcode.com/problems/edit-distance/
 */
class Solution_72 {

    // variable number of args
    private int minimum(int... nums) {
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
        }
        return min;
    }

    // edit distance
    // f(m,n) = (a[m]==b[n]) ? f(m-1,n-1) : min(f(m-1,n),f(m,n-1),f(m-1,,n-1))
    // Space : O(mxn)
    // time : o(mxn)
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + minimum(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    // decreasing the space complexity by storing the last row
    public int minDistance1(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[2][n + 1];
        int t = 0;
        int c = 0;
        int l;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                c = t % 2;
                l = (t + 1) % 2;
                if (i == 0) {
                    dp[c][j] = j;
                } else if (j == 0) {
                    dp[c][0] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[c][j] = dp[l][j - 1];
                } else {
                    dp[c][j] = 1 + minimum(dp[c][j - 1], dp[l][j], dp[l][j - 1]);
                }
            }
            t++;
        }
        return dp[c][n];
    }

    // top down approach
    public int minDistance2(String word1, String word2) {
        if (word1.length() == 0) return word2.length();
        if (word2.length() == 0) return word1.length();

        return match(word1.toCharArray(), word2.toCharArray(), 0, 0, new Integer[word1.length()][word2.length()]);
    }

    private int match(char[] s1, char[] s2, int i, int j, Integer[][] memo) {
        if (j == s2.length) return s1.length - i; // delete the remaining chars from s1
        if (i == s1.length) return s2.length - j; // insert the remaining chars to s1

        if (memo[i][j] != null) return memo[i][j];

        if (s1[i] == s2[j]) {
            memo[i][j] = match(s1, s2, i + 1, j + 1, memo);
        } else {
            int insert = match(s1, s2, i, j + 1, memo);
            int delete = match(s1, s2, i + 1, j, memo);
            int replace = match(s1, s2, i + 1, j + 1, memo);

            memo[i][j] = Math.min(insert, Math.min(delete, replace)) + 1;
        }

        return memo[i][j];
    }
}