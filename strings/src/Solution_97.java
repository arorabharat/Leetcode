public class Solution_97 {


    class Approach_1 {

        private char[] c1, c2, c3;
        int n1, n2, n3;

        public boolean isInterleave(int i, int j, int k) {
            if (i == n1 && j == n2 && k == n3) {
                return true;
            }
            if (i < n1 && k < n3 && c1[i] == c3[k] && isInterleave(i + 1, j, k + 1)) {
                return true;
            }
            return j < n2 && k < n3 && c2[j] == c3[k] && isInterleave(i, j + 1, k + 1);
        }

        public boolean isInterleave(String s1, String s2, String s3) {
            this.c1 = s1.toCharArray();
            this.c2 = s2.toCharArray();
            this.c3 = s3.toCharArray();
            this.n1 = s1.length();
            this.n2 = s2.length();
            this.n3 = s3.length();
            return isInterleave(0, 0, 0);
        }
    }

    class Approach_2 {


        public boolean isInterleave(String s1, String s2, String s3) {
            int n1, n2, n3;
            char[] c1, c2, c3;
            c1 = s1.toCharArray();
            c2 = s2.toCharArray();
            c3 = s3.toCharArray();
            n1 = s1.length();
            n2 = s2.length();
            n3 = s3.length();

            boolean[][][] dp = new boolean[n1 + 1][n2 + 1][n3 + 1];
            dp[0][0][0] = true;

            for (int i = 1; i <= n1 && i <= n3; i++) {
                if (c1[i - 1] == c3[i - 1]) {
                    dp[i][0][i] = dp[i - 1][0][i - 1];
                }
            }

            for (int j = 1; j <= n2 && j <= n3; j++) {
                if (c2[j - 1] == c3[j - 1]) {
                    dp[0][j][j] = dp[0][j - 1][j - 1];
                }
            }

            for (int i = 1; i <= n1; i++) {
                for (int j = 1; j <= n2; j++) {
                    for (int k = 1; k <= n3; k++) {
                        if (c1[i - 1] == c3[k - 1] && dp[i - 1][j][k - 1]) {
                            dp[i][j][k] = true;
                        }
                        if (c2[j - 1] == c3[k - 1] && dp[i][j - 1][k - 1]) {
                            dp[i][j][k] = true;
                        }
                    }
                }
            }
            return dp[n1][n2][n3];
        }
    }
}
