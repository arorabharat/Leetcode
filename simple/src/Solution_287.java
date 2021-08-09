/**
 * https://leetcode.com/problems/find-the-duplicate-number/
 */
class Solution_287 {

    private void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // cyclic array iteration
    public int findDuplicate(int[] nums) {
        int i = 0;
        while (nums[i] != i) {
            if (nums[nums[i]] == nums[i]) {
                return nums[i];
            }
            swap(i, nums[i], nums);
        }
        return nums[0];
    }


    /**
     * count([4,6,4,2,1,4,3,5]) = (1,2,3,6,7,8,8)
     * count of number less than or equal to value (i + 1 ) where i the index
     * BINARY_SEARCH
     */
    public int findDuplicate2(int[] nums) {
        int low = 1, high = nums.length - 1;
        int duplicate = -1;
        while (low <= high) {
            int cur = (low + high) / 2;
            // Count how many numbers in 'nums' are less than or equal to 'cur'
            int count = 0;
            for (int num : nums) {
                if (num <= cur)
                    count++;
            }
            if (count > cur) {
                duplicate = cur;
                high = cur - 1;
            } else {
                low = cur + 1;
            }
        }
        return duplicate;
    }
}
