/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
 */
class Solution_123 {

    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n < 2) return 0;
        int[] low = new int[n];
        int[] right = new int[n];
        int[] left = new int[n];
        int[] high = new int[n];

        low[0] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            low[i] = Math.min(low[i - 1], prices[i - 1]);
        }
        high[n - 1] = Integer.MIN_VALUE;
        for (int i = n - 2; i >= 0; i--) {
            high[i] = Math.max(high[i + 1], prices[i + 1]);
        }

        left[0] = 0;
        for (int i = 1; i < n; i++) {
            left[i] = Math.max(left[i - 1], prices[i] - low[i]);
        }
        right[n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = Math.max(right[i + 1], high[i] - prices[i]);
        }

        int profit = 0;
        for (int i = 0; i < n; i++) {
            profit = Math.max(left[i] + right[i], profit);
        }

        return profit;
    }

    /**
     * Approach 2
     * Optimised
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int[][] profit = new int[2][n];
        int buy = prices[0];
        for (int i = 0; i < n; i++) {
            profit[0][i] = Math.max(profit[0][i], prices[i] - buy);
            if (prices[i] < buy) {
                buy = prices[i];
            }
        }
        int sell = prices[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            profit[1][i] = Math.max(profit[1][i + 1], sell - prices[i]);
            if (prices[i] > sell) {
                sell = prices[i];
            }
        }
        int maxProfit = 0;
        for (int i = 0; i < n; i++) {
            maxProfit = Math.max(maxProfit, profit[0][i] + profit[1][i]);
        }
        return maxProfit;
    }

}
