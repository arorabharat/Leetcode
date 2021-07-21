import java.util.Arrays;

/**
 * https://leetcode.com/problems/valid-triangle-number/
 */
class Solution_611 {


    /**
     *  we fix the larger edge and look for the other two edges pair
     */
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            sum = sum + countSumGreater(nums, 0, i - 1, nums[i]);
        }
        return sum;
    }

    /**
     * If we fix the middle edge at e , then we could only take the edges for which nums[s] + nums[e] > target
     * We will find this s by decreasing the pointer one step at a time if we find the first combo which
     * satisfy the constraint then all the index after that s will satisfy the above constrains.
     * We choose another middle edge by decrement e-- once we found s and keep repeating this process.
     */
    private int countSumGreater(int[] nums, int s, int e, int target) {
        int sum = 0;
        while (s < e) {
            if (nums[s] + nums[e] <= target) {
                s++;
            } else {
                sum = sum + e - s;
                e--;
            }
        }
        return sum;
    }
}
