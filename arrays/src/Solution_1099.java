import java.util.Arrays;

/**
 * https://leetcode.com/problems/two-sum-less-than-k/
 */
class Solution_1099 {

    public int twoSumLessThanK(int[] nums, int k) {
        Arrays.sort(nums);
        int i = 0;
        int j = nums.length - 1;
        int ans = Integer.MIN_VALUE;
        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum < k) {
                i++;
                ans = Math.max(ans, sum);
            } else {
                j--;
            }
        }
        return (ans == Integer.MIN_VALUE) ? -1 : ans;
    }
}