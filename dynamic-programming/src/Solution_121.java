/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 *
 * @see Solution_122
 */
class Solution_121 {

    /**
     * Approach 1: Two arrays (min from left, max from right)
     * Time: O(n), Space: O(n)
     * max from the right gives best day after ith day when it would be optimal to sell the stock
     * min from left gives the best day before ith when it would be optimal to buy the stock
     * We calculate profit for all values of i, and then find max profit from those values
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[] maxFromRight = new int[n];
        int[] minFromLeft = new int[n];
        
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
            maxProfit = Math.max(maxProfit, maxFromRight[i] - minFromLeft[i]);
        }
        return maxProfit;
    }

    /**
     * Approach 2: Optimized - Track minimum price (Greedy)
     * Time: O(n), Space: O(1)
     * We track the minimum price encountered so far. For each day, if we sell today,
     * we would have bought at the minimum price to maximize profit.
     * Calculate max possible profit for each day and track the maximum.
     */
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        
        int minPrice = prices[0];
        int maxProfit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            // Update max profit if selling today gives better profit
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            // Update minimum price seen so far
            minPrice = Math.min(minPrice, prices[i]);
        }
        
        return maxProfit;
    }

    /**
     * Approach 3: Kadane's Algorithm (Maximum Subarray on differences)
     * Time: O(n), Space: O(1)
     * Convert the problem to finding maximum subarray sum of price differences.
     * If we buy on day i and sell on day j, profit = prices[j] - prices[i] = 
     * sum of (prices[k+1] - prices[k]) for k from i to j-1.
     * So we find the maximum subarray sum of consecutive differences.
     */
    public int maxProfit3(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int maxProfit = 0;
        int currentProfit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            // Kadane's algorithm: extend subarray or start new
            currentProfit = Math.max(0, currentProfit + diff);
            maxProfit = Math.max(maxProfit, currentProfit);
        }
        
        return maxProfit;
    }

    /**
     * Approach 4: Two-pointer / Sliding Window
     * Time: O(n), Space: O(1)
     * Use two pointers: buy (left) and sell (right).
     * Move right pointer forward, and if price[right] < price[buy], 
     * move buy pointer to right (found a better buying point).
     * Otherwise, calculate profit and update max.
     */
    public int maxProfit4(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int buy = 0;  // index of buying day
        int maxProfit = 0;
        
        for (int sell = 1; sell < prices.length; sell++) {
            if (prices[sell] < prices[buy]) {
                // Found a better buying point
                buy = sell;
            } else {
                // Calculate profit if we sell today
                maxProfit = Math.max(maxProfit, prices[sell] - prices[buy]);
            }
        }
        
        return maxProfit;
    }

    /**
     * Approach 5: State Machine DP
     * Time: O(n), Space: O(1)
     * Two states: 
     * - hold: maximum profit if we hold a stock (bought but not sold)
     * - sold: maximum profit if we've sold the stock
     * For single transaction: hold = max(hold, -price), sold = max(sold, hold + price)
     */
    public int maxProfit5(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int hold = -prices[0];  // Buy on first day
        int sold = 0;            // Haven't sold yet
        
        for (int i = 1; i < prices.length; i++) {
            // Either keep holding or buy today (only one transaction allowed)
            hold = Math.max(hold, -prices[i]);
            // Either keep sold state or sell today
            sold = Math.max(sold, hold + prices[i]);
        }
        
        return sold;
    }
}
