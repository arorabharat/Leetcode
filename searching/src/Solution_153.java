/**
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 * @see DSA#BINARY_SEARCH_ON_ROTATED_ARRAY
 */
class Solution_153 {

    private int binarySearch(int[] nums, int s, int e) {
        if (s < e) {
            int n = nums.length;
            int m = s + (e - s) / 2;
            if (m + 1 < n && nums[m] > nums[m + 1]) {
                return nums[m + 1];
            } else if (nums[m] > nums[n - 1]) {
                return binarySearch(nums, m + 1, e);
            } else {
                return binarySearch(nums, s, m);
            }
        }
        return nums[s];
    }

    public int findMin(int[] nums) {
        int n = nums.length;
        return binarySearch(nums, 0, n - 1);
    }
}
