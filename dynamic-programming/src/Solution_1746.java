/**
 * https://leetcode.com/problems/maximum-subarray-sum-after-one-operation/
 */
class Solution_1746 {

    /**
     * For each index calculate the maximum sum sub array you can make by going in right direction
     */
    private int[] maxSubArrayToRight(int[] nums) {
        int n = nums.length;
        if (n == 0) return new int[0];
        int[] dp = new int[n];
        dp[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            dp[i] = Math.max(dp[i + 1] + nums[i], nums[i]);
        }
        return dp;
    }

    /**
     * For each index calculate the maximum sum sub array you can make by going in left direction
     */
    private int[] maxSubArrayToLeft(int[] nums) {
        int n = nums.length;
        if (n == 0) return new int[0];
        int[] dp = new int[n];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        }
        return dp;
    }

    /**
     * For each index take the left sum , square the number at index and take the right sum to get the total sum
     */
    public int maxSumAfterOperation(int[] nums) {
        int n = nums.length;
        int maxSum = nums[0];
        int[] leftSumSubArray = maxSubArrayToLeft(nums);
        int[] rightSumSubArray = maxSubArrayToRight(nums);
        for (int i = 0; i < n; i++) {
            int leftSum = (i - 1 >= 0) ? leftSumSubArray[i - 1] : 0;
            int rightSum = (i + 1 < n) ? rightSumSubArray[i + 1] : 0;
            maxSum = Math.max(maxSum, leftSum + nums[i] * nums[i] + rightSum);
        }
        return maxSum;
    }
}