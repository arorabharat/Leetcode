import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/find-all-duplicates-in-an-array/
 */
class Solution_442 {

    private void swap(int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    /**
     * Approach 1
     * Cyclic sort
     */
    public List<Integer> findDuplicates(int[] nums) {
        int i = 0;
        int n = nums.length;
        List<Integer> result = new ArrayList<>();
        while (i < n) {
            int num = nums[i];
            if (num == -1 || num == i + 1) {
                i++;
            } else if (nums[num - 1] == num) {
                result.add(num);
                nums[i] = -1;
            } else {
                swap(nums, i, num - 1);
            }
        }
        return result;
    }

    /**
     * Approach 2
     * @see DSA#MARK_NEGATIVE_STRATEGY
     */
    public List<Integer> findDuplicates1(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for (int num : nums) {
            if (nums[Math.abs(num) - 1] < 0) { // seen before
                ans.add(Math.abs(num));
            }
            nums[Math.abs(num) - 1] *= -1;
        }
        return ans;
    }
}
