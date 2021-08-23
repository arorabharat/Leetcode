import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/implement-stack-using-queues/
 * TODO : wrong solution
 */
class Solution_225 {

    static class MyStack {

        private final Queue<Integer> inputOrder;
        private final Queue<Integer> outputOrder;


        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            inputOrder = new LinkedList<>();
            outputOrder = new LinkedList<>();
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            inputOrder.add(x);
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            while (!inputOrder.isEmpty()) {
                outputOrder.add(inputOrder.remove());
            }
            if (outputOrder.isEmpty()) {
                return Integer.MIN_VALUE;
            }
            return outputOrder.remove();
        }

        /**
         * Get the top element.
         */
        public int top() {
            while (!inputOrder.isEmpty()) {
                outputOrder.add(inputOrder.remove());
            }
            if (outputOrder.isEmpty()) {
                return Integer.MIN_VALUE;
            }
            return outputOrder.peek();
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return outputOrder.isEmpty() && inputOrder.isEmpty();
        }
    }
}
