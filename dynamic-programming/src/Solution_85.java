import java.util.Stack;

/**
 * ============================================================================================================================
 * https://leetcode.com/problems/maximal-rectangle/
 * ============================================================================================================================
 */
class Solution_85 {

    private int largestRectangleAreaInHistogram(int[] dp) {
        int COLS = dp.length;
        int width = 0;
        int height = 0;
        int area = 0;
        Stack<Integer> incStack = new Stack<>();
        incStack.push(-1);
        for (int j = 0; j < COLS; j++) {
            while (incStack.peek() != -1 && dp[incStack.peek()] >= dp[j]) {
                int index = incStack.pop();
                height = dp[index];
                width = (j - 1) - incStack.peek(); // most complicate part to think
                area = Math.max(area, width * height);
            }
            incStack.push(j);
        }
        while (incStack.peek() != -1) {
            int index = incStack.pop();
            height = dp[index];
            width = (COLS - 1) - incStack.peek();
            area = Math.max(area, width * height);
        }
        return area;
    }

    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length;
        if (n == 0) {
            return 0;
        }
        int m = matrix[0].length;
        int[] dp = new int[m];
        int area = 0;
        for (char[] chars : matrix) {
            for (int j = 0; j < m; j++) {
                dp[j] = (chars[j] == '1') ? dp[j] + 1 : 0;
            }
            area = Math.max(area, largestRectangleAreaInHistogram(dp));
        }
        return area;
    }
}
