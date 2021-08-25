import java.util.Stack;

/**
 * https://leetcode.com/problems/implement-queue-using-stacks/
 */
class Solution_232 {

    static class MyQueue {

        private final Stack<Integer> input;
        private final Stack<Integer> output;

        public MyQueue() {
            input = new Stack<>();
            output = new Stack<>();
        }

        public void push(int x) {
            input.push(x);
        }

        private void refillOutput() {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
        }

        /**
         * output order , reversed order is stored in the output stack
         * once the reversed element are finished in the stack then only we refill the stack
         * because lets say we got push(1), push(2), push(4), pop()
         * we will reverse the stack to 4 2 1 and then pop 1
         * but if we get push(5) push(6) pop() next
         * we can not push the element the output stack as the order will be messed up.
         */
        public int pop() {
            if (this.empty()) {
                return Integer.MIN_VALUE;
            }
            if (output.isEmpty()) {
                refillOutput();
            }
            return output.pop();
        }


        public int peek() {
            if (this.empty()) {
                return Integer.MIN_VALUE;
            }
            if (output.isEmpty()) {
                refillOutput();
            }
            return output.peek();
        }

        public boolean empty() {
            return input.isEmpty() && output.isEmpty();
        }
    }
}

