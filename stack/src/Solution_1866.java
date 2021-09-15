import java.util.Stack;
import java.util.stream.IntStream;

public class Solution_1866 {

    public int maxSumMinProduct(int[] nums) {
        int n = nums.length;
        long[] prefixSum = new long[n + 2];
        IntStream.range(0, n).forEach(i -> prefixSum[i + 1] = prefixSum[i] + nums[i]);
        Stack<Integer> stack = new Stack<>();
        int[] rightSmallerIndex = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }
            rightSmallerIndex[i] = (stack.isEmpty()) ? n : stack.peek();
            stack.push(i);
        }
        while (!stack.isEmpty()) stack.pop();
        int[] leftSmallerIndex = new int[n];
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }
            leftSmallerIndex[i] = (stack.isEmpty()) ? -1 : stack.peek();
            stack.push(i);
        }
        long max = 0;
        int mod = (int) Math.pow(10, 9) + 7;
        for (int i = 0; i < n; i++) {
            long rightSum = prefixSum[rightSmallerIndex[i]] - prefixSum[i];
            long leftSum = prefixSum[i + 1] - prefixSum[leftSmallerIndex[i] + 1];
            long mmp = nums[i] * (rightSum + leftSum - nums[i]);
            if (max < mmp) {
                max = mmp;
            }
        }
        return (int) (max % mod);
    }
}
