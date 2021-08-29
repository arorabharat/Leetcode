import java.util.Stack;

/**
 * ============================================================================================================================
 * https://leetcode.com/problems/sum-of-subarray-minimums/
 * ============================================================================================================================
 */
class Solution_907 {

    /**
     * Approach 1  : Brute force
     */
    public int sumSubarrayMins(int[] arr) {
        int ans = 0;
        int MODULO = (int) Math.pow(10, 9) + 7;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int min = arr[i];
            ans = (ans + arr[i]) % MODULO;
            for (int j = i + 1; j < n; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                }
                ans = (ans + min) % MODULO;
            }
        }
        return ans;
    }

    static class MonotonicallyDecStack<K extends Comparable<K>> extends Stack<K> {
        @Override
        public K push(K item) {
            while (!isEmpty() && this.peek().compareTo(item) <= 0) {
                this.pop();
            }
            return super.push(item);
        }
    }


    /**
     * 3  6  4  2
     * all possible sub array if the starting index is fixed.
     *
     * 2
     *
     * 4
     * 4 2
     *
     * 6
     * 6 4
     * 6 4 2
     *
     * 3
     * 3 6
     * 3 6 4
     * 3 6 4 2
     * 3 3 3 2 <= minimum of all subarray starting at 0 index
     *
     * so if we have to calculate the sum of all minimum of array starting at index i it would be
     * dp[i] = arr[i] * (index of next minimum element - i ) + d[index of next minimum element]
     *
     */
    public int sumSubarrayMins2(int[] arr) {
        int MODULO = (int) Math.pow(10, 9) + 7;
        int n = arr.length;
        Stack<Integer> incStack = new Stack<>();
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!incStack.isEmpty() && arr[incStack.peek()] >= arr[i]) {
                incStack.pop();
            }
            if (!incStack.isEmpty()) {
                dp[i] = (dp[i] + dp[incStack.peek()] + arr[i] * (incStack.peek() - i)) % MODULO;
            } else {
                dp[i] = (dp[i] + arr[i] * (n - i)) % MODULO;
            }
            incStack.push(i);
        }
        int ans = 0;
        for (int d : dp) {
            ans = (ans + d) % MODULO;
        }
        return ans;
    }

}