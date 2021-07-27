import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 * TODO : try using the deque
 */
class Solution_239 {

    static class FreqManager extends TreeMap<Integer, Integer> {

        public void decFreq(int element) {
            int count = this.getOrDefault(element, 0);
            count--;
            if (count > 0) {
                this.put(element, count);
            } else {
                this.remove(element);
            }
        }

        public void incFreq(int element) {
            this.put(element, this.getOrDefault(element, 0) + 1);
        }

        public Integer getHighestKey() {
            return this.lastKey();
        }
    }

    /**
     * Store the frequency of the each element in the k size window.
     * Use tree map which keeps the value in the sorted order.
     * Keep updating the frequency f the element as we move the window. Last key will be the largest key int the map.
     */
    public int[] maxSlidingWindow(int[] nums, int k) {

        FreqManager freqManager = new FreqManager();
        int n = nums.length;
        if (n == 0) return new int[0];
        int res_n = n - k + 1;
        int res_i = 0;
        int[] result = new int[res_n];
        for (int i = 0; i < k; i++) {
            freqManager.incFreq(nums[i]);
        }
        result[res_i] = freqManager.getHighestKey();
        res_i++;
        for (int j = k; j < n; j++) {
            freqManager.decFreq(nums[j - k]);
            freqManager.incFreq(nums[j]);
            result[res_i] = freqManager.getHighestKey();
            res_i++;
        }
        return result;
    }

    private void mark(int[] arr, int s, int e, int value) {
        int n = arr.length;
        for (int i = Math.max(s, 0); i < n && i <= e; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                arr[i] = value;
            }
        }
    }

    /**
     * Approach using the max heap,
     * find out the max element it will part of the windows covering it , hence we know answer for those windows.
     * Find out the next max element and mark the element for windows which does not have the max element yet
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int n = nums.length;

        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(Comparator.comparingInt(a -> -a[0]));
        for (int i = 0; i < n; i++) {
            maxHeap.add(new int[]{nums[i], i});
        }
        if (n <= k) {
            return new int[]{maxHeap.remove()[0]};
        }
        int res_n = n - (k - 1);
        int[] result = new int[res_n];
        for (int i = 0; i < res_n; i++) {
            result[i] = Integer.MIN_VALUE;
        }
        while (!maxHeap.isEmpty()) {
            int[] front = maxHeap.remove();
            int value = front[0];
            int index = front[1];
            mark(result, index - (k - 1), index, value);
        }
        return result;
    }
}