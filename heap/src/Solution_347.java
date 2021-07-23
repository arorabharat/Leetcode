import java.util.*;

/**
 * https://leetcode.com/problems/top-k-frequent-elements/
 * TODO
 */
class Solution_347 {


    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int n = map.size();
        int[][] freq = new int[n][2];
        int i = 0;
        for (int num : map.keySet()) {
            freq[i][0] = num;
            freq[i][1] = map.get(num);
            i++;
        }
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(Comparator.comparingInt(a -> -a[1]));
        for (int j = 0; j < n; j++) {
            maxHeap.add(freq[j]);
        }
        int[] ans = new int[k];
        for (int j = 0; j < k; j++) {
            int[] front = maxHeap.remove();
            ans[j] = front[0];
        }
        return ans;
    }

    /**
     * Approach 2
     * Slight optimisation over approach 1
     */
    public int[] topKFrequent2(int[] nums, int k) {
        // O(1) time
        if (k == nums.length) {
            return nums;
        }

        // 1. build hash map : character and how often it appears
        // O(N) time
        Map<Integer, Integer> count = new HashMap<>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }

        // init minHeap 'the less frequent element first'
        Queue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(count::get));

        // 2. keep k top frequent elements in the minHeap
        // O(N log k) < O(N log N) time
        for (int n : count.keySet()) {
            minHeap.add(n);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // 3. build an output array
        // O(k log k) time
        int[] top = new int[k];
        for (int i = k - 1; i >= 0; --i) {
            top[i] = minHeap.poll();
        }
        return top;
    }

    void quickSort(int[] nums, Map<Integer, Integer> count, int s, int e) {
        // TODO : implement it using the quick sort
    }

    /**
     * Approach 3
     *
     * @see DSA#QUICK_SORT
     * Using quick sort
     */
    public int[] topKFrequent3(int[] nums, int k) {
        // O(1) time
        if (k == nums.length) {
            return nums;
        }

        Map<Integer, Integer> count = new HashMap<>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }
        quickSort(nums, count, 0, nums.length - 1);
        int[] top = new int[k];
        for (int i = k - 1; i >= 0; --i) {
        }
        return top;
    }
}