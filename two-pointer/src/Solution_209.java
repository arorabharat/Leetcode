/**
 * https://leetcode.com/problems/minimum-size-subarray-sum/
 */
class Solution_209 {

    /**
     * We maintain two pointer
     * ith element point to first element
     * jth pointer points to the next element that can be include in the window
     * if the sum is greater than or equal to the target we take len
     * otherwise we keep on incrementing the jth pointer
     */
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        int i = 0;
        int j = 1;
        int sum = nums[0];
        int minArrayLen = Integer.MAX_VALUE;
        while (j < n) {
            if (sum < target) {
                sum = sum + nums[j];
                j++;
            } else {
                minArrayLen = Math.min(j - i, minArrayLen);
                sum = sum - nums[i];
                i++;
            }
        }
        while (sum >= target) {
            minArrayLen = Math.min(j - i, minArrayLen);
            sum = sum - nums[i];
            i++;
        }
        return minArrayLen == Integer.MAX_VALUE ? 0 : minArrayLen;
    }
}