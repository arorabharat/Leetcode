import java.util.Random;

/**
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 */
class Solution_215 {

    Random rand = new Random();

    void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        k = n - k;
        int start = 0;
        int end = n - 1;
        while (start < end) {
            int last = start;
            int random = start + rand.nextInt(end + 1 - start);
            swap(nums, random, end);
            for (int i = start; i < end; i++) {
                if (nums[i] < nums[end]) {
                    swap(nums, i, last);
                    last++;
                }
            }
            swap(nums, last, end);
            if (last < k) {
                start = last + 1;
            } else if (last == k) {
                return nums[last];
            } else {
                end = last - 1;
            }
        }
        return nums[start];
    }
}