public class Solution_918 {
    public int maxSubarraySumCircular(int[] nums) {
        int length = nums.length;
        if (length == 0) return 0;
        int start = 0;
        int end = 1;
        int sum = nums[start];
        int maxSum = sum;
        while (end < length) {
            sum = sum + nums[end];
            if (sum < 0) {
                start++;
                end = start + 1;
                sum = nums[start];
            }
            end++;
        }
        return maxSum;
    }
}
