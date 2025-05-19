/**
 * https://leetcode.com/problems/target-sum/
 */
class Solution_494 {


    class Solution1 {

        public int findTargetSumWays(int[] nums, int target) {

            return 0;
        }
    }

    class Solution2 {

        public int findTargetSumWays(int[] nums, int S) {
            int m = 1001;
            int n = nums.length;
            int[][] dp = new int[2 * m + 1][30];
            dp[m][0] = 1;
            for (int j = 1; j <= n; j++) {
                for (int i = -m; i <= m; i++) {
                    int subtract = i + nums[j - 1];
                    if (subtract + m >= 0 && subtract + m < 2 * m + 1) {
                        dp[i + m][j] = dp[i + m][j] + dp[subtract + m][j - 1];
                    }
                    int add = i - nums[j - 1];
                    if (add + m >= 0 && add + m < 2 * m + 1) {
                        dp[i + m][j] = dp[i + m][j] + dp[add + m][j - 1];
                    }
                }
            }
            if (-m < S && S < m) {
                return dp[S + m][n];
            }
            return 0;
        }
    }

}