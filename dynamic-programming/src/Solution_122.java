/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
 *
 * @see Solution_121
 */
class Solution_122 {

    /**
     * Approach 1
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int buy = prices[0];
        int profit = 0;
        int currProfit = 0;
        for (int i = 1; i < n; i++) {
            if (prices[i] - buy < currProfit) {
                profit = profit + currProfit;
                buy = prices[i];
                currProfit = 0;
            } else {
                currProfit = prices[i] - buy;
            }
        }
        profit = profit + currProfit;
        return profit;
    }

    /**
     * Approach 2 : Optimisation
     */
    public int maxProfit1(int[] p) {
        if (p.length == 1) return 0;
        int n = p.length;
        int profit = 0;
        for (int i = 1; i < n; i++) {
            if (p[i] - p[i - 1] > 0) {
                profit = profit + p[i] - p[i - 1];
            }
        }
        return profit;
    }
}

