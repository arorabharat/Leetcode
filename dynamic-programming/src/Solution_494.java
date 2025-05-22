import java.util.Arrays;

/**
 * https://leetcode.com/problems/target-sum/
 */
class Solution_494 {


    // recursive solution
    class Solution1 {

        public int _findTargetSumWays(int[] nums, int index, int sum) {
            if (sum == 0 && index == -1) return 1;
            if (sum < 0) return 0;
            if (index < 0) return 0;
            return _findTargetSumWays(nums, index - 1, sum - nums[index]) + _findTargetSumWays(nums, index - 1, sum);
        }

        // brute force solution.
        public int findTargetSumWays(int[] nums, int target) {
            int sum = Arrays.stream(nums).sum();
            int buketSum = (sum + target) / 2;
            int buketModulo = (sum + target) % 2;
            if (buketModulo == 1) return 0;
            return _findTargetSumWays(nums, nums.length - 1, buketSum);
        }
    }

    class Solution2 {

        public int _findTargetSumWays(int[] nums, int index, int sum) {
            if (sum == 0 && index == -1) return 1;
            if (sum < 0) return 0;
            if (index < 0) return 0;
            return _findTargetSumWays(nums, index - 1, sum - nums[index]) + _findTargetSumWays(nums, index - 1, sum);
        }

        // brute force solution.
        public int findTargetSumWays(int[] nums, int target) {
            int sum = Arrays.stream(nums).sum();
            int buketSum = (sum + target) / 2;
            int buketModulo = (sum + target) % 2;
            if (buketModulo == 1) return 0;
            int n = nums.length;
            int[][] dp = new int[n + 1][buketSum + 1];
            dp[0][0] = 1;
            for (int i = 1; i <= n; i++) {
                for (int s = 1; s <= buketSum; s++) {
                    dp[i][s] = dp[i - 1][s];
                    if (sum >= nums[i]) {
                        dp[i][s] += dp[i - 1][s - nums[i - 1]];
                    }
                }
            }
            return dp[n][buketSum];
        }
    }


    class Solution3 {

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