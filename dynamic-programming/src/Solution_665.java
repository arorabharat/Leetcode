/**
 * https://leetcode.com/problems/non-decreasing-array/
 * TODO
 */
class Solution_665 {

    public boolean checkPossibility(int[] nums) {
        int n = nums.length;
        if (n < 3) return true;
        int base = Integer.MIN_VALUE;
        boolean flip = true;
        if(nums[0] > nums[1] && nums[1] > nums[2]) {
            return false;
        }
        else if(nums[0] <= nums[1] && nums[1] <= nums[2] ) {
            base = nums[2];
        }
        else if(nums[1] <= nums[2]) {
            flip = false;
            base = nums[2];
        }
        else if(nums[0] <= nums[2]) {
            flip = false;
            base = nums[2];
        }
        else if(nums[0] <= nums[1]) {
            flip = false;
            base = nums[1];
        }
        // System.out.println(base);
        for (int i = 3; i < n; i++) {
            if(base <= nums[i]) {
                base = nums[i];
                System.out.println(" "+base);
            }
            else if(flip) {
                flip = false;
                // System.out.println(" flip ");
            }
            else{
                return false;
            }
        }
        return true;
    }
}