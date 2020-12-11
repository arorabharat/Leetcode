import java.util.Stack;

public class Solution_20 {

    boolean matching(char o, char c) {
        boolean pair1 = o == '(' && c == ')';
        boolean pair2 = o == '{' && c == '}';
        boolean pair3 = o == '[' && c == ']';
        return pair1 || pair2 || pair3;
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] arr = s.toCharArray();
        for (char c : arr) {
            if (stack.isEmpty()) {
                stack.push(c);
            } else if (matching(stack.peek(), c)) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}

