/**
 * https://leetcode.com/problems/3sum-smaller/
 * TODO
 */
class Solution_259 {

    /**
     * brute force approach
     */
    public int threeSumSmaller(int[] nums, int target) {
        int n = nums.length;
        if (n < 3) return 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] < target) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

}

