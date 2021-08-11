import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Solution_496 {

    /**
     * Create a monotonically increasing stack.
     * lets say for index i the next greater element is at index j
     * for all the elements at index i - 1 if the answer is not i then it would also not be between i to j-1
     * Given the next greater element for the i is j.
     * <p>
     * Based on this observation we build a increasing stack
     *
     * @see DSA#INCREASING_STACK
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int n = nums2.length;
        int[] ans = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                ans[i] = stack.peek();

            } else {
                ans[i] = -1;
            }
            stack.add(nums2[i]);
        }
        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i < n; i++) {
            index.put(nums2[i], i);
        }
        int k = nums1.length;
        int[] ans2 = new int[k];
        for (int i = 0; i < k; i++) {
            ans2[i] = ans[index.get(nums1[i])];
        }
        return ans2;
    }
}