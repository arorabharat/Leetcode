import java.util.Arrays;

/**
 * https://leetcode.com/problems/wiggle-sort/
 */
class Solution_280 {

    /**
     * Easiest approach is to sort the array and put the elements
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

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void wiggleSort2(int[] nums) {
        int n = nums.length;
        boolean dec = true;
        for (int i = 1; i < n; i++) {
            if (dec) {
                if (nums[i - 1] > nums[i]) {
                    swap(nums, i - 1, i);
                }
            } else {
                if (nums[i - 1] < nums[i]) {
                    swap(nums, i - 1, i);
                }
            }
            dec = !dec;
        }
    }
}
