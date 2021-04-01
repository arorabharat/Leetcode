/**
 * https://leetcode.com/problems/min-cost-climbing-stairs/
 */
class Solution_746 {

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n];

        for (int i = 2; i < n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        return Math.min(dp[n - 1] + cost[n - 1], dp[n - 2] + cost[n - 2]);
    }

    /**
     * Optimised
     * Cost of reaching the current index would be cost paid at index + minimum of cost of reaching any of the previous two index ( because jump 1 or 2 step )
     * Similarly keep calculating the cost until we reach last two index of the array and answer is minimum of those.
     */
    public int minCostClimbingStairs2(int[] costs) {
        int lastSecond = 0;
        int last = 0;
        for (int cost : costs) {
            int curr = cost + Math.min(last, lastSecond);
            lastSecond = last;
            last = curr;
        }
        return Math.min(last, lastSecond);
    }

}