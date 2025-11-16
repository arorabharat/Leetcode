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

    class Approach_1 {

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

    class Approach_2 {

        public int minimumTotal(List<List<Integer>> triangle) {
            int r = triangle.size();
            int inf = Integer.MAX_VALUE;
            if (r == 0) return inf;
            int[][] dp = new int[r][r];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < r; j++) {
                    dp[i][j] = inf;
                }
            }
            dp[0][0] = triangle.get(0).get(0);
            for (int i = 1; i < r; i++) {
                for (int j = 0; j <= i; j++) {
                    int v = triangle.get(i).get(j);
                    if (dp[i - 1][j] != inf) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + v);
                    }
                    if (j > 0 && dp[i - 1][j - 1] != inf) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + v);
                    }
                }
            }
            int minCost = dp[r - 1][0];
            for (int i = 1; i < r; i++) {
                minCost = Math.min(dp[r - 1][i], minCost);
            }
            return minCost;
        }

    }
}
