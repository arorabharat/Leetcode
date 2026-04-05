import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    class Solution2 {
        public int mincostTickets(int[] days, int[] costs) {
            // The last day on which we need to travel.
            int lastDay = days[days.length - 1];
            int[] dp = new int[lastDay + 1];
            Arrays.fill(dp, 0);

            int i = 0;
            for (int day = 1; day <= lastDay; day++) {
                // If we don't need to travel on this day, the cost won't change.
                if (day < days[i]) {
                    dp[day] = dp[day - 1];
                } else {
                    // Buy a pass on this day, and move on to the next travel day.
                    i++;
                    // Store the cost with the minimum of the three options.
                    dp[day] = Math.min(dp[day - 1] + costs[0],
                            Math.min(dp[Math.max(0, day - 7)] + costs[1],
                                    dp[Math.max(0, day - 30)] + costs[2]));
                }
            }

            return dp[lastDay];
        }
    }

    class Solution3 {
        Set<Integer> isTravelNeeded = new HashSet<>();

        private int solve(int[] dp, int[] days, int[] costs, int currDay) {
            // If we have iterated over travel days, return 0.
            if (currDay > days[days.length - 1]) {
                return 0;
            }

            // If we don't need to travel on this day, move on to next day.
            if (!isTravelNeeded.contains(currDay)) {
                return solve(dp, days, costs, currDay + 1);
            }

            // If already calculated, return from here with the stored answer.
            if (dp[currDay] != -1) {
                return dp[currDay];
            }

            int oneDay = costs[0] + solve(dp, days, costs, currDay + 1);
            int sevenDay = costs[1] + solve(dp, days, costs, currDay + 7);
            int thirtyDay = costs[2] + solve(dp, days, costs, currDay + 30);

            // Store the cost with the minimum of the three options.
            return dp[currDay] = Math.min(oneDay, Math.min(sevenDay, thirtyDay));
        }

        public int mincostTickets(int[] days, int[] costs) {
            // The last day on which we need to travel.
            int lastDay = days[days.length - 1];
            int[] dp = new int[lastDay + 1];
            Arrays.fill(dp, -1);

            // Mark the days on which we need to travel.
            for (int day : days) {
                isTravelNeeded.add(day);
            }

            return solve(dp, days, costs, 1);
        }
    }
}