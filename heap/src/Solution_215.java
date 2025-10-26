import java.util.PriorityQueue;

public class Solution_215 {

    class Approach1 {

        public int findKthLargest(int[] nums, int k) {
            if (k == 0) return Integer.MIN_VALUE;
            int n = nums.length;
            if (n == 0 || k > n) return Integer.MIN_VALUE;
            PriorityQueue<Integer> q = new PriorityQueue<>();
            for (int c : nums) {
                q.add(c);
                if (q.size() > k) {
                    q.remove();
                }
            }
            return q.peek();
        }
    }
}
