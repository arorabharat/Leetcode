/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
 */
class Solution_188 {

    int[][] partitionProfitDp;
    int[][] maxProfitDp;

    private int profitForPartition(int[] prices, int s, int e) {
        if (partitionProfitDp[s][e] != Integer.MIN_VALUE) {
            return partitionProfitDp[s][e];
        }
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = s; i <= e; i++) {
            if (maxProfit < (prices[i] - minPrice)) {
                maxProfit = prices[i] - minPrice;
            }
            minPrice = Math.min(minPrice, prices[i]);
        }
        partitionProfitDp[s][e] = maxProfit;
        return maxProfit;
    }


    private int _maxProfit(int k, int[] prices, int s) {
        if (k == 0) return 0;
        if (s == prices.length) return 0;
        if (maxProfitDp[s][k] != Integer.MIN_VALUE) {
            return maxProfitDp[s][k];
        }
        int max = 0;
        for (int i = s; i < prices.length; i++) {
            int partitionProfit = profitForPartition(prices, s, i);
            int restProfit = _maxProfit(k - 1, prices, i + 1);
            max = Math.max(partitionProfit + restProfit, max);
        }
        maxProfitDp[s][k] = max;
        return max;
    }

    public int maxProfit(int k, int[] prices) {
        int M = 1001;
        int K = 101;
        partitionProfitDp = new int[M][M];
        maxProfitDp = new int[M][K];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                partitionProfitDp[i][j] = Integer.MIN_VALUE;
            }
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < K; j++) {
                maxProfitDp[i][j] = Integer.MIN_VALUE;
            }
        }
        return _maxProfit(k, prices, 0);
    }
}
