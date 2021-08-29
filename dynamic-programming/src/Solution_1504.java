/**
 * ============================================================================================================================
 * https://leetcode.com/problems/count-submatrices-with-all-ones/
 * ============================================================================================================================
 */
class Solution_1504 {
    /**
     *
     * 1 0 1 1
     * 1 1 1 1
     * 1 0 1 1
     * 1 1 0 0
     *
     * max horizontal height
     * 1 0 1 2
     * 1 2 3 4
     * 1 0 1 2
     * 1 2 0 0
     *
     * max vertical height
     * 1 0 1 1
     * 2 1 2 3
     * 3 0 3 3
     * 4 1 0 0
     *
     *  NUmber of options for left top corner (min of columns) :    1   1   2    3
     *                                                              2   1   2    3 <=== right, bottom corner
     *
     * To calculate the number of square that can be made where index of 3 is the right bottom corner,
     * we have multiple option to choose the left top corner based on the column we select. that would be the minimum height of columns
     * we would have traversed in the backward direction
     *
     */
    public int numSubmat(int[][] mat) {
        int n = mat.length;
        if (n == 0) return 0;
        int m = mat[0].length;
        int[][] maxH = new int[n + 1][m + 1];
        int[][] maxV = new int[n + 1][m + 1];
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (mat[i - 1][j - 1] == 1) {
                    maxH[i][j] = maxH[i][j - 1] + 1;
                    maxV[i][j] = maxV[i - 1][j] + 1;
                }
                int k = j;
                int count = maxH[i][j];
                int maxHeightLeftTopCorner = Integer.MAX_VALUE;
                while (1 <= k && count > 0) {
                    maxHeightLeftTopCorner = Math.min(maxV[i][k], maxHeightLeftTopCorner);
                    sum = sum + maxHeightLeftTopCorner;
                    k--;
                }
            }
        }
        return sum;
    }
}