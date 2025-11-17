import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
 */
public class Solution_862 {

    // brute force
    class Approach_1 {

        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length;
            int minLen = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                int s = 0;
                for (int j = i; j < n; j++) {
                    s = s + nums[j];
                    if (s >= k || (j - i + 1 >= minLen)) {
                        minLen = Math.min(minLen, j - i + 1);
                        break;
                    }
                }
            }
            return (minLen == Integer.MAX_VALUE) ? -1 : minLen;
        }
    }

    class Approach_2 {


        // [-2, 100 , -1, -1, -1, -1 , 5, 100]

        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length;
            int minLen = Integer.MAX_VALUE;
            int startIndex = -1;
            int prefixSum = 0;
            for (int endIndex = 0; endIndex < n; endIndex++) {
                int v = nums[endIndex];
                prefixSum = prefixSum + v;
                if (prefixSum <= 0) {
                    prefixSum = 0;
                    startIndex = endIndex;
                } else if (prefixSum >= k) {
                    minLen = Math.min(minLen, endIndex - startIndex);
                    while (prefixSum - nums[startIndex + 1] >= k) {
                        prefixSum = prefixSum - nums[startIndex + 1];
                        startIndex++;
                    }
                }
            }
            return (minLen == Integer.MAX_VALUE) ? -1 : minLen;
        }
    }

    // [-2, 100 , -1, -1, -1, -1 , 5, 100]

    class Approach_3 {
        public int shortestSubarray(int[] A, int K) {
            int n = A.length;
            long[] P = new long[n + 1];
            for (int i = 0; i < n; ++i) P[i + 1] = P[i] + A[i];

            int ans = n + 1;
            Deque<Integer> dq = new ArrayDeque<>();

            for (int j = 0; j <= n; ++j) {
                // Try to shorten from the front while condition met
                while (!dq.isEmpty() && P[j] - P[dq.peekFirst()] >= K) {
                    ans = Math.min(ans, j - dq.pollFirst());
                }
                // Maintain increasing order of prefix sums in deque
                while (!dq.isEmpty() && P[j] <= P[dq.peekLast()]) {
                    dq.pollLast();
                }
                dq.addLast(j);
                System.out.println(dq);
            }

            return ans <= n ? ans : -1;
        }
    }
}
