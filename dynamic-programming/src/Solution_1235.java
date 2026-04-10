import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Solution_1235 {

    class Solution {

        class Job {

            int s;
            int e;
            int p;

            public Job(int s, int e, int p) {
                this.s = s;
                this.e = e;
                this.p = p;
            }
        }

        public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
            int n = startTime.length;
            int[] dp = new int[n];
            Job[] jobs = new Job[n];
            for (int i = 0; i < n; i++) {
                jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
            }
            Arrays.sort(jobs, Comparator.comparingInt(j -> j.s));
            int maxProfit = 0;
            for (int i = 0; i < n; i++) {
                dp[i] = jobs[i].p;
                for (int j = 0; j < i; j++) {
                    if (jobs[j].e <= jobs[i].s) {
                        dp[i] = Math.max(dp[i], dp[j] + jobs[i].p);
                    }
                }
                maxProfit = Math.max(maxProfit, dp[i]);
            }
            return maxProfit;
        }
    }

    class Solution2 {

        class Job {

            int s;
            int e;
            int p;

            public Job(int s, int e, int p) {
                this.s = s;
                this.e = e;
                this.p = p;
            }
        }

        public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
            int n = startTime.length;
            int[] dp = new int[n];
            Job[] jobs = new Job[n];
            for (int i = 0; i < n; i++) {
                jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
            }
            Arrays.sort(jobs, Comparator.comparingInt(j -> j.e));
            int maxProfit = 0;
            dp[0] = jobs[0].p;
            for (int i = 1; i < n; i++) {
                dp[i] = Math.max(dp[i - 1], jobs[i].p);
                int j = binarySearch(jobs, i);
                if (j >= 0) {
                    dp[i] = Math.max(dp[i], dp[j] + jobs[i].p);
                }

                maxProfit = Math.max(maxProfit, dp[i]);
            }
            return maxProfit;
        }

        private int binarySearch(Job[] jobs, int i) {
            int s = 0;
            int e = i - 1;
            int ceil = jobs[i].s;
            int prev = -1;
            while (s <= e) {
                int m = s + (e - s) / 2;
                if (jobs[m].e <= ceil) {
                    prev = m;
                    s = m + 1;
                } else {
                    e = m - 1;
                }
            }
            return prev;
        }
    }
}
