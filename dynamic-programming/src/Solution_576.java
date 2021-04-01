/**
 * https://leetcode.com/problems/out-of-boundary-paths/
 */
class Solution_576 {

    private boolean inRange(int i, int j, int m, int n) {
        return 0 <= i && i < m && 0 <= j && j < n;
    }

    /**
     * if we can only one move we have to be at the edge of the matrix.
     * number of ways for one move will be equal to the number of the boundary edge cell touch.
     * if we current cell can go out of boundary in the k - 1 stpe then the adjacent cell can go out of boundary in the k steps.
     * Finally we will sum k = 1,2 .. maxMove to get the final answer
     * Space complexity : O(m * n * maxMove)
     * Time Complexity : O(m * n * maxMove)
     */
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {

        long[][][] dp = new long[m][n][maxMove + 1];
        int[] dr = {-1, 0, 0, 1};
        int[] dc = {0, 1, -1, 0};

        int mod = (int) Math.pow(10, 9) + 7;
        for (int k = 1; k <= maxMove; k++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (k == 1) {
                        if (i == 0) dp[i][j][k]++;
                        if (i == m - 1) dp[i][j][k]++;
                        if (j == 0) dp[i][j][k]++;
                        if (j == n - 1) dp[i][j][k]++;
                    } else {
                        for (int l = 0; l < 4; l++) {
                            int r = i + dr[l];
                            int c = j + dc[l];
                            if (inRange(r, c, m, n)) {
                                dp[r][c][k] = (dp[r][c][k] + dp[i][j][k - 1]) % mod;
                            }
                        }
                    }
                }
            }
        }
        long ans = 0;
        for (int k = 1; k <= maxMove; k++) {
            ans = (ans + dp[startRow][startColumn][k]) % mod;
        }
        return (int) ans;
    }
    //
    // k=1 :  3 2 3
    // k=2 :  2 6 2
    // k=3 :  6 4 6
    // answer = 2 + 6 + 4
}
