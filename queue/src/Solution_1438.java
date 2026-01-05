import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Solution_1438 {

    class Approach_1 {

        class Solution {

            class MinQueue {

                Deque<Integer> iq = new LinkedList<>();
                Queue<Integer> q = new LinkedList<>();

                void add(int v) {
                    q.add(v);
                    while (!iq.isEmpty() && iq.peekLast() > v) {
                        iq.removeLast();
                    }
                    iq.addLast(v);
                }

                int remove() {
                    int front = q.remove();
                    if (!iq.isEmpty() && front == iq.peekFirst()) {
                        iq.removeFirst();
                    }
                    return front;
                }

                int min() {
                    return (iq.isEmpty()) ? -1 : iq.peekFirst();
                }

                int size() {
                    return q.size();
                }
            }

            class MaxQueue {

                Deque<Integer> dq = new LinkedList<>();
                Queue<Integer> q = new LinkedList<>();

                void add(int v) {
                    q.add(v);
                    while (!dq.isEmpty() && dq.peekLast() < v) {
                        dq.removeLast();
                    }
                    dq.addLast(v);
                }

                void remove() {
                    int front = q.remove();
                    if (!dq.isEmpty() && front == dq.peekFirst()) {
                        dq.removeFirst();
                    }
                }

                int max() {
                    return (dq.isEmpty()) ? -1 : dq.peekFirst();
                }

                int size() {
                    return q.size();
                }
            }

            class MinMaxQueue {

                MinQueue minq = new MinQueue();
                MaxQueue maxq = new MaxQueue();

                MinMaxQueue() {
                }

                int diff() {
                    return maxq.max() - minq.min();
                }

                void remove() {
                    minq.remove();
                    maxq.remove();
                }

                void add(int v) {
                    minq.add(v);
                    maxq.add(v);
                }

                int size() {
                    return minq.size();
                }
            }

            public int longestSubarray(int[] nums, int limit) {
                MinMaxQueue minmaxq = new MinMaxQueue();
                int n = nums.length;
                if (n == 0) return 0;
                int len = 0;
                for (int v : nums) {
                    while (minmaxq.diff() > limit) {
                        minmaxq.remove();
                    }
                    len = Math.max(len, minmaxq.size());
                    minmaxq.add(v);
                }
                while (minmaxq.diff() > limit) {
                    minmaxq.remove();
                }
                len = Math.max(len, minmaxq.size());
                return len;
            }
        }
    }

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
