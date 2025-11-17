import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
 */
public class Solution_862 {

    // brute force - O(n^2) time, O(1) space
    class Approach_1 {
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length;
            int minLen = Integer.MAX_VALUE;
            
            for (int i = 0; i < n; i++) {
                long sum = 0;
                for (int j = i; j < n; j++) {
                    sum += nums[j];
                    if (sum >= k) {
                        minLen = Math.min(minLen, j - i + 1);
                        // Once we find a valid subarray starting at i, 
                        // we can break since we're looking for shortest
                        break;
                    }
                }
            }
            
            return minLen == Integer.MAX_VALUE ? -1 : minLen;
        }
    }

    // Optimized sliding window with prefix sum - O(n) time, O(n) space
    // This approach uses prefix sums to handle negative numbers correctly
    // [-2, 100 , -1, -1, -1, -1 , 5, 100]
    class Approach_2 {
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length;
            long[] prefixSum = new long[n + 1];
            
            // Build prefix sum array
            for (int i = 0; i < n; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
            
            int minLen = Integer.MAX_VALUE;
            Deque<Integer> deque = new ArrayDeque<>();
            
            for (int i = 0; i <= n; i++) {
                // Remove indices from front if we found a valid subarray
                // We want to find the shortest, so we keep removing from front
                while (!deque.isEmpty() && prefixSum[i] - prefixSum[deque.peekFirst()] >= k) {
                    minLen = Math.min(minLen, i - deque.pollFirst());
                }
                
                // Maintain deque with increasing prefix sums
                // If current prefix sum is <= last prefix sum, remove last
                // because if prefixSum[i] <= prefixSum[j] and i > j,
                // then prefixSum[i] is always better (shorter distance, same or better sum)
                while (!deque.isEmpty() && prefixSum[i] <= prefixSum[deque.peekLast()]) {
                    deque.pollLast();
                }
                
                deque.addLast(i);
            }
            
            return minLen == Integer.MAX_VALUE ? -1 : minLen;
        }
    }
}
