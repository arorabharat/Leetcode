/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
 * 
 * Problem: You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * Find the maximum profit you can achieve. You may complete as many transactions as you like 
 * (i.e., buy one and sell one share of the stock multiple times).
 * 
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 * @see Solution_121
 */
class Solution_122 {

    /**
     * Approach 1: Greedy - Capture every positive price difference
     * Time: O(n), Space: O(1)
     * 
     * Intuition: Since we can make multiple transactions, we should capture every 
     * opportunity where price increases. If price goes up from day i to i+1, 
     * we can buy on day i and sell on day i+1 to capture that profit.
     * 
     * This is optimal because any profit from buying on day i and selling on day j
     * can be broken down into sum of consecutive day profits: 
     * (prices[j] - prices[i]) = (prices[i+1] - prices[i]) + ... + (prices[j] - prices[j-1])
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    /**
     * Approach 2: Peak-Valley Approach
     * Time: O(n), Space: O(1)
     * 
     * Intuition: Buy at valleys (local minima) and sell at peaks (local maxima).
     * We traverse the array and identify when we're at a valley (price starts increasing)
     * and when we're at a peak (price starts decreasing).
     */
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int profit = 0;
        int i = 0;
        int n = prices.length;
        
        while (i < n - 1) {
            // Find valley (local minimum)
            while (i < n - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            int valley = prices[i];
            
            // Find peak (local maximum)
            while (i < n - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            int peak = prices[i];
            
            profit += peak - valley;
        }
        
        return profit;
    }

    /**
     * Approach 3: Dynamic Programming - State Machine
     * Time: O(n), Space: O(1)
     * 
     * Intuition: At each day, we can be in one of two states:
     * - hold: We hold a stock (either bought today or holding from before)
     * - sold: We don't hold a stock (either sold today or didn't hold before)
     * 
     * Since we can make unlimited transactions, we can buy and sell on the same day
     * (buy in the morning, sell in the evening).
     */
    public int maxProfit3(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int hold = -prices[0];  // Buy on first day
        int sold = 0;            // No stock held initially
        
        for (int i = 1; i < prices.length; i++) {
            // Either keep holding or buy today (can buy even if we sold yesterday)
            int newHold = Math.max(hold, sold - prices[i]);
            // Either keep sold state or sell today (can sell if we're holding)
            int newSold = Math.max(sold, hold + prices[i]);
            
            hold = newHold;
            sold = newSold;
        }
        
        return sold;  // Final state should be sold (no stock held)
    }

    /**
     * Approach 4: Optimized DP with single variables
     * Time: O(n), Space: O(1)
     * 
     * Same as Approach 3 but more concise variable usage.
     */
    public int maxProfit4(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int hold = -prices[0];
        int sold = 0;
        
        for (int i = 1; i < prices.length; i++) {
            sold = Math.max(sold, hold + prices[i]);
            hold = Math.max(hold, sold - prices[i]);
        }
        
        return sold;
    }

    /**
     * Approach 5: Recursive with Memoization (Top-down DP)
     * Time: O(n), Space: O(n) for recursion stack and memo
     * 
     * Intuition: For each day, we can either:
     * - Buy (if we don't hold) or do nothing
     * - Sell (if we hold) or do nothing
     * 
     * This approach is less efficient but demonstrates the recursive structure.
     */
    public int maxProfit5(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        
        int n = prices.length;
        Integer[][] memo = new Integer[n][2];  // [day][hold: 0=no, 1=yes]
        
        return dfs(prices, 0, 0, memo);  // Start at day 0, not holding
    }
    
    private int dfs(int[] prices, int day, int holding, Integer[][] memo) {
        if (day >= prices.length) return 0;
        
        if (memo[day][holding] != null) {
            return memo[day][holding];
        }
        
        int profit = 0;
        if (holding == 0) {
            // Not holding: can buy or skip
            int buy = dfs(prices, day + 1, 1, memo) - prices[day];
            int skip = dfs(prices, day + 1, 0, memo);
            profit = Math.max(buy, skip);
        } else {
            // Holding: can sell or skip
            int sell = dfs(prices, day + 1, 0, memo) + prices[day];
            int skip = dfs(prices, day + 1, 1, memo);
            profit = Math.max(sell, skip);
        }
        
        memo[day][holding] = profit;
        return profit;
    }
}

