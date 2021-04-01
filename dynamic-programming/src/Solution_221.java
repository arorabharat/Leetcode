/**
 * https://leetcode.com/problems/maximal-square/
 */
class Solution_221 {

    private void isOne(int[][][] dp, char[][] matrix, int x1, int y1, int l) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (x1 < m && y1 < n && l < Math.min(m - x1, n - y1)) {
            if (dp[x1][y1][l] == -1) {
                if (l == 0) {
                    dp[x1][y1][l] = (matrix[x1][y1] - '0');
                } else {
                    isOne(dp, matrix, x1 + 1, y1, l - 1);
                    isOne(dp, matrix, x1, y1 + 1, l - 1);
                    isOne(dp, matrix, x1 + 1, y1 + 1, l - 1);
                    dp[x1][y1][l] = matrix[x1][y1] - '0' & dp[x1 + 1][y1][l - 1] & dp[x1][y1 + 1][l - 1] & dp[x1 + 1][y1 + 1][l - 1];
                }
            }
        }
    }

    private int largestSquare(int[][][] dp, int m, int n, int o) {
        int len = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    if (dp[i][j][k] == 1) {
                        len = Math.max(len, Math.abs(k + 1));
                    }
                }
            }
        }
        return len;
    }

    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        if (m == 0) return 0;
        int n = matrix[0].length;
        int o = Math.min(m, n);
        int[][][] dp = new int[m][n][o];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    isOne(dp, matrix, i, j, k);
                }
            }
        }

        int len = largestSquare(dp, m, n, o);
        return len * len;
    }

    public int maximalSquare1(char[][] matrix) {
        int h = matrix.length;
        int w = matrix[0].length;
        int[][] t = new int[h][w];
        int max = 0;

        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                if (matrix[r][c] == '1') {
                    t[r][c] = 1;
                    if (r > 0 && c > 0) {
                        t[r][c] = Math.min(Math.min(t[r - 1][c], t[r][c - 1]), t[r - 1][c - 1]) + 1;//minimum of last square + me
                    }
                    max = Math.max(max, t[r][c]);
                }

            }
        }
        return max * max;
    }
}
