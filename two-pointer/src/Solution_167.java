/**
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * @see DSA#TWO_POINTER
 */
class Solution_167 {

    public int[] twoSum(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            int a = nums[i];
            int b = nums[j];
            if (a + b == target) {
                return new int[]{i + 1, j + 1};
            } else if (a + b < target) {
                i++;
            } else {
                j--;
            }
        }
        return new int[]{-1, -1};
    }
}