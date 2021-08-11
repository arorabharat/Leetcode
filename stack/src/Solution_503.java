import java.util.Stack;

/**
 * https://leetcode.com/problems/next-greater-element-ii/
 */
class Solution_503 {

    void _nextGreaterElements(Stack<Integer> stack, int[] nums, int[] ans) {
        int n = nums.length;
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            ans[i] = (stack.isEmpty()) ? -1 : stack.peek();
            stack.add(nums[i]);
        }
    }

    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int n = nums.length;
        int[] ans = new int[n];
        _nextGreaterElements(stack, nums, ans);
        _nextGreaterElements(stack, nums, ans);
        return ans;
    }
}
