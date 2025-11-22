import java.util.Deque;
import java.util.LinkedList;

public class Solution_155 {

    class MinStack_1 {

        // 5, 6,7, 3 , 2 , 4 , 5 , 1
        Deque<Integer> regularStack;
        Deque<Integer> minStack;


        public MinStack_1() {
            this.minStack = new LinkedList<>();
            this.regularStack = new LinkedList<>();
        }

        public void push(int val) {
            this.regularStack.offerLast(val);
            if (this.minStack.isEmpty() || this.minStack.peekLast() >= val) {
                this.minStack.offerLast(val);
            }
        }

        public void pop() {
            if (!this.regularStack.isEmpty()) {
                int removedValue = this.regularStack.pollLast();
                if (!this.minStack.isEmpty() && removedValue == this.minStack.peekLast()) {
                    this.minStack.pollLast();
                }
            }
        }

        public int top() {
            return (this.regularStack.isEmpty()) ? Integer.MAX_VALUE : this.regularStack.peekLast();
        }

        public int getMin() {
            return (this.minStack.isEmpty()) ? Integer.MAX_VALUE : this.minStack.peekLast();
        }
    }

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
}
