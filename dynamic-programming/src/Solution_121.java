import java.util.Arrays;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 */
class Solution_121 {

    /**
     * max from the right give best day after ith day when it would be optimal to sell the stock
     * min from left gives the best day before ith when it would be optimal to buy the stock
     * This we calculate profit for all the values of i, and then profit max profit from those values
     */
    public int maxProfit(int[] prices) {

        int n = prices.length;
        int[] maxFromRight = new int[n];
        int[] minFromLeft = new int[n];
        Arrays.fill(minFromLeft, Integer.MAX_VALUE);
        Arrays.fill(maxFromRight, Integer.MIN_VALUE);
        minFromLeft[0] = prices[0];
        for (int i = 1; i < n; i++) {
            minFromLeft[i] = Math.min(minFromLeft[i - 1], prices[i]);
        }
        maxFromRight[n - 1] = prices[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxFromRight[i] = Math.max(maxFromRight[i + 1], prices[i]);
        }
        int maxProfit = 0;
        for (int i = 0; i < n; i++) {
            int profitForDay = maxFromRight[i] - minFromLeft[i];
            maxProfit = Math.max(maxProfit, profitForDay);
        }
        return maxProfit;
    }

    /**
     * Optimised :
     * We will store the minimum price we have encountered till now if we have to sell today then we would assume that we bought the stock when price was minimum to maximise profit
     * So, in this way we calculate, max possible profit for each day and calculate the max of all the profit
     */
    public int maxProfit2(int[] prices) {
        int minimumStockPriceEncountered = 0;
        int profit = 0;
        for (int price : prices) {
            int maxProfitIfSellToday = price - minimumStockPriceEncountered;
            if (price < minimumStockPriceEncountered) {
                minimumStockPriceEncountered = price;
            } else if (maxProfitIfSellToday > profit) {
                profit = maxProfitIfSellToday;
            }
        }
        return profit;
    }
}
