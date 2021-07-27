import java.util.Arrays;

/**
 * https://leetcode.com/problems/wiggle-sort/
 * TODO : implement without sorting thr array
 */
class Solution_280 {

    /**
     * Easiset approach is to sort the array and put the elements
     */
    public void wiggleSort(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        System.arraycopy(nums, 0, ans, 0, n);
        Arrays.sort(ans);
        int k = n - 1;
        for (int i = 1; i < n; i = i + 2) {
            nums[i] = ans[k];
            k--;
        }
        for (int i = 0; i < n; i = i + 2) {
            nums[i] = ans[k];
            k--;
        }
    }
}
