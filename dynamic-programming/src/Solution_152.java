import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/maximum-product-subarray/
 * TODO
 * Level#GOOD
 */
class Solution_152 {

    /**
     * Time Complexity :  O( N^2 )
     * Space Complexity :  O( 1 )
     */
    public int maxProduct(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        long ans = nums[0];
        for (int i = 0; i < n; i++) {
            long p = nums[i];
            ans = Math.max(ans, p);
            for (int j = i + 1; j < n; j++) {
                p = p * nums[j];
                ans = Math.max(ans, p);
            }
        }
        return (int) ans;
    }

    /**
     * Wrong approach
     */
    public int maxProduct2(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        Deque<Integer> queue = new LinkedList<>();
        long ans = nums[0];
        int neg = 0;
        for (int num : nums) {
            if (num == 0) {
                long p = getAns(queue, neg);
                ans = Math.max(ans, p);
            } else if (num < 0) {
                queue.addLast(num);
                neg++;
            } else {
                int temp = num;
                if (!queue.isEmpty() && queue.peek() > 0) {
                    temp = queue.removeLast() * num;
                }
                queue.addLast(temp);
            }
        }
        return (int) ans;
    }

    private long getAns(Deque<Integer> queue, int neg) {
        long p = 1;
        if (neg % 2 == 0) {
            int left = 1;
            if (!queue.isEmpty() && queue.getFirst() > 0) {
                left = left * queue.getFirst();
            }
            int right = 1;
            if (!queue.isEmpty() && queue.getLast() > 0) {
                right = right * queue.getLast();
            }
            for (Integer x : queue) {
                p = p * x;
            }
            p = Math.max(p / ((long) queue.getFirst() * left), p / ((long) queue.getLast() * right));

        } else {
            for (Integer x : queue) {
                p = p * x;
            }
        }
        while (!queue.isEmpty()) {
            queue.removeLast();
        }
        return p;
    }
}
