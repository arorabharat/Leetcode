/**
 * https://github.com/arorabharat/Leetcode/tree/main/arrays/src/Solution_53.java
 */
class Solution_53 {


    public int maxSubArray1(int[] nums) {
        int length = nums.length;
        int[][] dp = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                if (j == i) {
                    dp[i][j] = nums[j];
                } else {
                    dp[i][j] = dp[i][j - 1] + nums[j];
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }


    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int num : nums) {
            sum = sum + num;
            maxSum = Math.max(maxSum, sum);
            // we are resetting the value after taking the max because of the array contains all the negative number then or answer will be zero
            // which is incorrect - it should be greatest negative number
            if (sum <= 0) {
                sum = 0;
            }
        }
        return maxSum;
    }
}