import java.util.*;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 */
class Solution_239 {

    class Solution1 {
        /**
         * Store the frequency of the element in the k size window.
         * Use a tree map which keeps the value in the sorted order.
         * Keep updating the frequency of the element as we move the window.
         * The Last key will be the largest key into the map.
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
    }

    class Solution3 {

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


    public class Solution2 {

        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            if (k <= 0 || k > n) {
                return new int[0];
            }

            MaxQueue<Integer> maxQueue = new MaxQueue<>();
            int[] result = new int[n - k + 1];

            // Fill the first window
            for (int i = 0; i < k; i++) {
                maxQueue.offer(nums[i]);
            }
            result[0] = maxQueue.max();

            for (int i = k; i < n; i++) {
                maxQueue.offer(nums[i]);
                maxQueue.poll(); // remove the leftmost element of the window
                result[i - k + 1] = maxQueue.max();
            }

            return result;
        }

        static class MaxQueue<T extends Comparable<T>> {
            private final Queue<T> queue = new LinkedList<>();
            private final Deque<T> deque = new LinkedList<>();

            public void offer(T value) {
                queue.offer(value);
                while (!deque.isEmpty() && deque.peekLast().compareTo(value) < 0) {
                    deque.pollLast();
                }
                deque.offerLast(value);
            }

            public T poll() {
                T removed = queue.poll();
                if (removed != null && removed.equals(deque.peekFirst())) {
                    deque.pollFirst();
                }
                return removed;
            }

            public T max() {
                return deque.peekFirst();
            }

            public boolean isEmpty() {
                return queue.isEmpty();
            }
        }
    }
}