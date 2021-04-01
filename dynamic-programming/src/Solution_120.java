import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/triangle/
 */
class Solution_120 {

    /*
         0 1 2 3 4
       0 ∞ 0 ∞ ∞ ∞
       1 ∞ 2 ∞ ∞ ∞
       2 ∞ 3 4 ∞ ∞
       3 ∞ 6 5 7 ∞
       4 ∞ 4 1 8 3

     */

    private int minInArray(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int e : arr) {
            min = Math.min(min, e);
        }
        return min;
    }

    private void initialiseWithInfinity(int[][] arr) {
        for (int[] ints : arr) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int m = triangle.size();
        if (m == 0) return 0;
        int[][] dp = new int[m + 1][m + 1];
        initialiseWithInfinity(dp);
        dp[0][1] = 0; // base case
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i][j] = triangle.get(i - 1).get(j - 1) + Math.min(dp[i - 1][j - 1], dp[i - 1][j]);
            }
        }
        return minInArray(dp[m]);
    }
}
