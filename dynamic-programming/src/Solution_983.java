import java.util.Arrays;

public class Solution_983 {

    class Solution {

        int[] days;
        int[] costs;

        // binary search replacement
        // TC - O(N)
        int getIndex(int s, int duration) {
            int it = s;
            while (it < this.days.length && this.days[it] < this.days[s] + duration) {
                it++;
            }
            return it;
        }

        private int _mincostTickets(int s, int[] results) {
            if (s == days.length) {
                return 0;
            }
            if (results[s] != 0) {
                return results[s];
            }
            int cost1 = _mincostTickets(s + 1, results) + this.costs[0];
            int cost7 = _mincostTickets(getIndex(s, 7), results) + this.costs[1];
            int cost30 = _mincostTickets(getIndex(s, 30), results) + this.costs[2];
            int minCost = Math.min(Math.min(cost1, cost7), cost30);
            results[s] = minCost;
            return minCost;
        }

        public int mincostTickets(int[] days, int[] costs) {
            this.days = days;
            this.costs = costs;
            Arrays.sort(days);
            int[] results = new int[this.days.length];
            return _mincostTickets(0, results);
        }
    }

    class Solution1 {

        public int mincostTickets(int[] days, int[] costs) {
            Arrays.sort(days);
            int length = days.length;
            int[] dp = new int[length + 1];
            if (length == 0) {
                return 0;
            }
            dp[0] = 0;
            for (int i = 0; i < length; i++) {
                dp[i + 1] = costs[0] + dp[i];
                int j = getStartDayIndex(days, i, 7);
                dp[i + 1] = Math.min(dp[i + 1], dp[j + 1] + costs[1]);
                int k = getStartDayIndex(days, i, 30);
                dp[i + 1] = Math.min(dp[i + 1], dp[k + 1] + costs[2]);
            }
            return dp[length];
        }

        private static int getStartDayIndex(int[] days, int i, int duration) {
            int startDayPass = days[i] - duration;
            int j = i;
            while (j >= 0 && days[j] > startDayPass) {
                j--;
            }
            return j;
        }
    }
}