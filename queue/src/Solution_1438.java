import java.util.LinkedList;
import java.util.Queue;

public class Solution_1438 {

    /**
     * brute force solution O(N^2)
     */
    public int longestSubarray(int[] nums, int limit) {
        int length = nums.length;
        if (length == 0) return 0;
        if (limit < 0) return -1;
        int maxSubarray = 1;
        for (int i = 0; i < length; i++) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(nums[i]);
            int j = i + 1;
            int min = nums[i] - limit;
            int max = nums[i] + limit;
            while (j < length && min <= nums[j] && nums[j] <= max) {
                queue.add(nums[j]);
                min = Math.max(min, nums[j] - limit);
                max = Math.min(max, nums[j] + limit);
                maxSubarray = Math.max(maxSubarray, queue.size());
                j++;
            }
        }
        return maxSubarray;
    }
}
