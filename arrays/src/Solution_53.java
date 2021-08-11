/**
 * https://github.com/arorabharat/Leetcode/tree/main/arrays/src/Solution_53.java
 */
class Solution_53 {

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