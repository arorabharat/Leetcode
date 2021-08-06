import java.util.Arrays;

/**
 * https://leetcode.com/problems/paint-house/
 */
class Solution_256 {

    /**
     * f(n,R) = min( f(n-1,G), f(n-1,B) ) + cost[n,R]
     */
    public int minCost(int[][] costs) {
        int c = 3;
        int n = costs.length;
        if (n == 0) return 0;
        int[][] dp = new int[n][c];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < c; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        // base case : initialise the cost of painting once house
        System.arraycopy(costs[0], 0, dp[0], 0, c);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < c; j++) {
                for (int k = 0; k < c; k++) {
                    if (j != k) {
                        dp[i][j] = Math.min(dp[i - 1][k] + costs[i][j], dp[i][j]);
                    }
                }
            }
        }
        int totalCost = Integer.MAX_VALUE;
        for (int j = 0; j < c; j++) {
            totalCost = Math.min(totalCost, dp[n - 1][j]);
        }
        return totalCost;
    }

    private void swapArray(int[] curr,int[] prev) {

    }

    /**
     * Space optimisation
     * We just previous row to calculate the current row, so we can optimize over space
     */
    public int minCost2(int[][] costs) {
        int c = 3;
        int n = costs.length;
        if (n == 0) return 0;
        int[] currRow = new int[c];
        int[] prevRow = new int[c];

        // base case : initialise the cost of painting once house
        System.arraycopy(costs[0], 0, currRow, 0, c);

        for (int i = 1; i < n; i++) {
            // swap array
            int[] temp = prevRow;
            prevRow = currRow;
            currRow = temp;
            Arrays.fill(currRow, Integer.MAX_VALUE);
            for (int j = 0; j < c; j++) {
                for (int k = 0; k < c; k++) {
                    if (j != k) {
                        currRow[j] = Math.min(prevRow[k] + costs[i][j], currRow[j]);
                    }
                }
            }
        }
        int totalCost = Integer.MAX_VALUE;
        for (int j = 0; j < c; j++) {
            totalCost = Math.min(totalCost, currRow[j]);
        }
        return totalCost;
    }
}