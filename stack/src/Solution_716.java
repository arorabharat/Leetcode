import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * https://leetcode.com/problems/max-stack/
 */
class Solution_716 {


    /**
     * Your MaxStack object will be instantiated and called as such:
     * MaxStack obj = new MaxStack();
     * obj.push(x);
     * int param_2 = obj.pop();
     * int param_3 = obj.top();
     * int param_4 = obj.peekMax();
     * int param_5 = obj.popMax();
     */
    static class MaxStack {

        Stack<Integer> allElements;
        PriorityQueue<Integer> maxHeap;

        /**
         * initialize your data structure here.
         */
        public MaxStack() {
            this.allElements = new Stack<>();
            this.maxHeap = new PriorityQueue<>(Comparator.comparingInt(a -> -a));
        }

        /**
         * Time Complexity :  O( 1 )
         * Space Complexity :  O( 1 )
         */
        public void push(int x) {
            this.allElements.push(x); // O(1)
            this.maxHeap.add(x); // O(1)
        }

        /**
         * Time Complexity :  O( N )
         * Space Complexity :  O( 1 )
         */
        public int pop() {
            if (!this.allElements.isEmpty()) {
                Integer element = this.allElements.pop(); // O(1)
                this.maxHeap.remove(element); // O(N)
                return element;
            }
            return Integer.MIN_VALUE;
        }

        /**
         * Time Complexity :  O( 1 )
         * Space Complexity :  O( 1 )
         */
        public int top() {
            if (!this.allElements.isEmpty()) return this.allElements.peek(); //O(1)
            return Integer.MIN_VALUE;
        }

        /**
         * Time Complexity :  O( 1 )
         * Space Complexity :  O( 1 )
         */
        public int peekMax() {
            if (!this.maxHeap.isEmpty()) return this.maxHeap.peek(); // O(1)
            return Integer.MIN_VALUE;
        }

        /**
         * Time Complexity :  O( N )
         * Space Complexity :  O( N )
         */
        public int popMax() {
            if (!this.maxHeap.isEmpty()) {
                Integer element = this.maxHeap.remove(); // O(1)
                Stack<Integer> tempStack = new Stack<>(); // O(N)
                while (!this.allElements.isEmpty() && !this.allElements.peek().equals(element)) {
                    tempStack.add(this.allElements.pop());
                }
                if (!this.allElements.isEmpty()) this.allElements.pop();
                while (!tempStack.isEmpty()) {
                    this.allElements.add(tempStack.pop());
                }
                return element;
            }
            return Integer.MIN_VALUE;
        }
    }

    /**
     * @see Level#GOOD
     */
    static class MaxStack2 {
        Stack<Integer> stack;
        Stack<Integer> maxStack;

        public MaxStack2() {
            stack = new Stack<>();
            maxStack = new Stack<>();
        }

        public void push(int x) {
            if (maxStack.isEmpty()) {
                maxStack.push(x);
            } else {
                maxStack.push(Math.max(maxStack.peek(), x));
            }
            stack.push(x);
        }

        public int pop() {
            maxStack.pop();
            return stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int peekMax() {
            return maxStack.peek();
        }

        public int popMax() {
            int max = peekMax();
            Stack<Integer> buffer = new Stack();
            while (top() != max) buffer.push(pop());
            pop();
            while (!buffer.isEmpty()) push(buffer.pop());
            return max;
        }
    }

}
