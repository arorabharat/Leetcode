/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
 */
class Solution_714 {

    /**
     * [1, 5, 2, 8, 4, 9]
     * 2 + 4 + 3
     * [1, 3, 2, 8, 4, 9]
     * 5 + 3
     */
    public int maxProfit(int[] p, int f) {
        int n = p.length;
        if (n < 2) {
            return 0;
        }
        int b = p[0];
        int peak = p[0];
        int profit = 0;
        boolean stock = false;
        for (int i = 1; i < n; i++) {

            if (b + f < p[i]) {
                stock = true;
            }

            if (stock && p[i] + f < peak) {
                profit = profit + (peak - b - f);
                b = p[i];
                peak = p[i];
                stock = false;
            }

            if (stock && peak < p[i]) {
                peak = p[i];
            }
            if (!stock && p[i] < b) {
                b = peak = p[i];
            }
        }
        if (stock && peak > b + f) {
            profit = profit + (peak - b - f);
        }

        return profit;
    }
}