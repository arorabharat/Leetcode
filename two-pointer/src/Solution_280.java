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

    /**
     * lets say the i-1 index are wiggle sort
     * At the ith index either we want decreasing or increasing seq
     * case 1 : decreasing but it is increasing i-i <= i
     * in that case i-2,i-1,i lets swap => i-2,i,i-1
     * as the i-2 =< i-1 <= i  ---> i-2 =< i >= i-1 we get ith index wiggle sorted without disturbing the i-1 index.
     * Case 2 : increasing but it is decreasing i-1>= i. We could swap
     */
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
