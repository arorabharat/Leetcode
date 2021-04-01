import java.util.Stack;

class Solution_946 {

    /**
     * Key points to note here is that numbers are distinct otherwise problem would be much difficult to solve.
     * Pop can only be done if the element is same as the last pushed element
     * Alternative you can make the pop from the existing element on the top of stack.
     * So I will need to keep on pushing the new elements until I find element which I need to pop.
     * once I am out of new elements to push in stack , only option is to use existing element in the stack for pop operations
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int i = 0;
        int j = 0;
        int in = pushed.length;
        int jn = popped.length;
        Stack<Integer> stack = new Stack<>();
        while (i < in && j < jn) {
            // check in pop can operation be performed using exiting element in stack
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                j++;
                stack.pop();
            }
            // keep pushing on the stack
            while (i < in && j<jn && pushed[i] != popped[j]) {
                stack.push(pushed[i]);
                i++;
            }
            // check if the element matched and pop can be made
            if (i<in && j<jn && pushed[i] == popped[j]) {
                i++;
                j++;
            } else {
                return false;
            }
        }
        while (!stack.isEmpty() && stack.peek() == popped[j]) {
            j++;
            stack.pop();
        }
        return stack.isEmpty() && i == in && j == jn;
    }
}