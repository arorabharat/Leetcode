/**
 * https://leetcode.com/problems/increasing-triplet-subsequence/
 */
class Solution_334 {

    /**
     * Approach 1
     * Create min array which contains minimum from left
     * Create max array which contains maximum from right
     * Check for each index if we have min element lesser than current element on left and maximum element on the right greater than current element.
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
     * Level approach, could be used even for four element in creasing order.
     * We set the level look for the element for next level.
     *
     * Time Complexity :  O( N )
     * Space Complexity :  O( 1 )
     */
    public boolean increasingTriplet2(int[] nums) {
        int firstLevel = Integer.MAX_VALUE;
        int secondLevel = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= firstLevel) {
                firstLevel = n;
            } else if (n <= secondLevel) {
                secondLevel = n;
            } else {
                return true;
            }
        }
        return false;
    }
}