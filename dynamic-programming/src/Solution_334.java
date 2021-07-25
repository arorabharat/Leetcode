/**
 * https://leetcode.com/problems/increasing-triplet-subsequence/
 */
class Solution_334 {

    /**
     * Approach 1
     * Time Complexity :  O( N )
     * Space Complexity :  O( N )
     */
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if (n == 0) return false;
        int[] min = new int[n];
        min[0] = nums[0];
        for (int i = 1; i < n; i++) {
            min[i] = Math.min(min[i - 1], nums[i]);
        }
        int[] max = new int[n];
        max[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            max[i] = Math.max(max[i + 1], nums[i]);
        }
        for (int i = 1; i < n - 1; i++) {
            if (min[i - 1] < nums[i] && nums[i] < max[i + 1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Approach 2
     * Time Complexity :  O( N )
     * Space Complexity :  O( 1 )
     */
    public boolean increasingTriplet2(int[] nums) {
        int first_num = Integer.MAX_VALUE;
        int second_num = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= first_num) {
                first_num = n;
            } else if (n <= second_num) {
                second_num = n;
            } else {
                return true;
            }
        }
        return false;
    }
}