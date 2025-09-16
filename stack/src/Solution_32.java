import java.util.Stack;

/**
 *
 */
public class Solution_32 {

    // this is O(N^2) solution hence it will give TLE
    class Approach_1 {

        public int longestValidParentheses(String s) {
            int n = s.length();
            boolean[] b = new boolean[n];
            for (int i = 0; i < n; i++) {
                b[i] = s.charAt(i) == '(';
            }
            int maximumLen = 0;
            for (int i = 0; i < n; i++) {
                int j = i;
                Stack<Boolean> charStack = new Stack<>();
                while (j < n) {
                    if (b[j]) {
                        charStack.add(b[j]);
                    } else if (!charStack.isEmpty() && charStack.peek()) {
                        charStack.pop();
                        if (charStack.isEmpty()) {
                            maximumLen = Math.max(j - i + 1, maximumLen);
                        }
                    } else {
                        if (charStack.isEmpty()) {
                            maximumLen = Math.max(j - i, maximumLen);
                        }
                        break;
                    }
                    j++;
                }
            }
            return maximumLen;
        }
    }
}
