import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class Solution_1856 {

    public class MinQ {

        int[] nums;

        public MinQ(int[] nums) {
            this.nums = nums;
        }

        private final Deque<Integer> incQ = new ArrayDeque<>();
        private final Deque<Integer> q = new ArrayDeque<>();

        public void addLast(int i) {
            if (i < 0 || i > nums.length) {
                throw new NullPointerException("element cannot be null");
            }
            q.addLast(i);
            while (!incQ.isEmpty() && nums[incQ.peekLast()] > nums[i]) {
                incQ.pollLast();
            }
            incQ.addLast(i);
        }

        public int removeFirst() {
            if (q.isEmpty()) return -1;
            int r = q.removeFirst(); // may throw if empty
            if (!incQ.isEmpty() && r == incQ.peekFirst()) {
                incQ.removeFirst();
            }
            return r;
        }

        public int peekMin() {
            if (incQ.isEmpty()) {
                throw new NoSuchElementException("queue is empty");
            }
            return incQ.peekFirst();
        }

        public int size() {
            return q.size();
        }

        public boolean isEmpty() {
            return q.isEmpty();
        }
    }


    public int maxSumMinProduct(int[] nums) {
        MinQ minQ = new MinQ(nums);
        int left = 0;
        int right = 0;
        int n = nums.length;
        int globalMinProduct = 0;
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        while (right < n) {
            minQ.addLast(right);
            while (minQ.peekMin() == right) {
                minQ.addLast(right);
                right++;
            }

            sum = sum + nums[right];

            if (globalMinProduct < minProduct) {
                globalMinProduct = minProduct;
            }
            right++;
        }
        return
    }
}


