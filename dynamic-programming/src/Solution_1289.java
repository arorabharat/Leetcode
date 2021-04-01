import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-falling-path-sum-ii/
 */
class Solution_1289 {
    private int minPath(int[][] arr, int currRow, int lastPickedCol, int C, int[][] dp) {
        int path = Integer.MAX_VALUE;
        for (int col = 0; col < C; col++) {
            if (col != lastPickedCol) {
                if (currRow == 0) {
                    path = Math.min(path, arr[currRow][col]);
                    continue;
                }
                if (dp[currRow - 1][col] == Integer.MAX_VALUE) {
                    dp[currRow - 1][col] = minPath(arr, currRow - 1, col, C, dp);
                }
                path = Math.min(path, arr[currRow][col] + dp[currRow - 1][col]);
            }
        }
        return path;
    }

    public int minFallingPathSum(int[][] arr) {
        int C = arr[0].length;
        int R = arr.length;
        int[][] dp = new int[R][C];
        for (int i = 0; i < R; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        return minPath(arr, R - 1, -1, C, dp);
    }
}