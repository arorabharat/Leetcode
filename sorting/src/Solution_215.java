import java.util.PriorityQueue;
import java.util.Random;

/**
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 */
class Solution_215 {

    Random rand = new Random();

    void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    /**
     * @see DSA#QUICK_SORT
     */
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        k = n - k;
        int start = 0;
        int end = n - 1;
        while (start < end) {
            int firstIndexGrtPivot = start;
            // pick pivot
            int random = start + rand.nextInt(end + 1 - start);
            swap(nums, random, end);
            // sort the element according to pivot
            for (int curr = start; curr < end; curr++) {
                if (nums[curr] < nums[end]) {
                    swap(nums, curr, firstIndexGrtPivot);
                    firstIndexGrtPivot++;
                }
            }
            swap(nums, firstIndexGrtPivot, end);
            // update the range
            if (firstIndexGrtPivot < k) {
                start = firstIndexGrtPivot + 1;
            } else if (firstIndexGrtPivot == k) {
                return nums[firstIndexGrtPivot];
            } else {
                end = firstIndexGrtPivot - 1;
            }
        }
        return nums[start];
    }

    /**
     * Approach 2 : using min heap
     * we will min heap where top element is the minimum element we have have observed till now
     * if k < n
     * then once the heap size is more than the k we will have to drop smallest element,
     * so once all the elements are considered heap will have k largest element and
     * out of which the smallest (kth largest)" will be on the top.
     */
    public int findKthLargest3(int[] nums, int k) {
        if (k > nums.length) return Integer.MIN_VALUE;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.remove();
            }
        }
        return (minHeap.isEmpty()) ? Integer.MIN_VALUE : minHeap.peek();
    }
}