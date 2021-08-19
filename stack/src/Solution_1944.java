import java.util.Stack;

class Solution_1944 {

    public int[] canSeePersonsCount(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int n = heights.length;
        int[] ans = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int count = 0;
            while (!stack.isEmpty() && stack.peek() < heights[i]) {
                count++;
                stack.pop();
            }
            ans[i] = count + (stack.isEmpty() ? 0 : 1);
            stack.add(heights[i]);
        }
        return ans;
    }
}