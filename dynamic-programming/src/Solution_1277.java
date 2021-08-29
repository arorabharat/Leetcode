/**
 * ============================================================================================================================
 * https://leetcode.com/problems/count-square-submatrices-with-all-ones/
 * https://github.com/arorabharat/Leetcode/tree/main/dynamic-programming/src/Solution_1277.java
 * ============================================================================================================================
 *
 */
class Solution_1277 {

    int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    /**
     * Time Complexity :  O( N*M )
     * Space Complexity :  O( N*M )
     *
     * For each cell ew would calculate the maximum size of the square we could make if the cell is bottom right corner of the square
     *
     * Once we know the maximum size of the square for each cell, we would all the maximum size to get the final answer
     * because if maximum size if 3 we could make 3 different square fro that cell as right bottom corner
     */
    public int countSquares(int[][] matrix) {
        int n = matrix.length;
        if (n == 0) return 0;
        int m = matrix[0].length;
        int[][] dp = new int[1 + n][1 + m];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (matrix[i - 1][j - 1] == 1) {
                    dp[i][j] = 1 + min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum = sum + dp[i][j];
            }
        }
        return sum;
    }
}