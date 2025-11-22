import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * https://leetcode.com/problems/min-stack/
 * ============================================================================================================================
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * ============================================================================================================================
 */
public class Solution_155 {

    /**
     * Approach 1: Two Stacks (Optimized)
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n) - worst case when all elements are in decreasing order
     * 
     * Optimization: Use Stack instead of Deque for clarity, and simplify the logic
     */
    static class MinStack_1 {
        private Stack<Integer> stack;
        private Stack<Integer> minStack;

        public MinStack_1() {
            this.stack = new Stack<>();
            this.minStack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            // Only push to minStack if it's empty or val is <= current min
            // Using <= handles duplicates correctly
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            // LeetCode guarantees valid operations, but adding check for safety
            if (stack.isEmpty()) return;
            
            int removedValue = stack.pop();
            // Only pop from minStack if the removed value equals current min
            if (removedValue == minStack.peek()) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    /**
     * Approach 2: Single Stack with Pairs (Value, Min)
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n) - stores pair for each element
     * 
     * Trade-off: More space per element but simpler logic, no need to sync two stacks
     */
    static class MinStack_2 {
        private Stack<int[]> stack; // [value, minSoFar]

        public MinStack_2() {
            this.stack = new Stack<>();
        }

        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(new int[]{val, val});
            } else {
                int currentMin = stack.peek()[1];
                stack.push(new int[]{val, Math.min(val, currentMin)});
            }
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek()[0];
        }

        public int getMin() {
            return stack.peek()[1];
        }
    }

    /**
     * Approach 3: Single Stack with Differences (Space Optimized)
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n) - but more space efficient when values are close to min
     * 
     * Optimization: Store differences instead of actual values to save space
     * Best when values are large but differences are small
     */
    static class MinStack_3 {
        private Stack<Long> stack; // Store differences as long to handle overflow
        private long min; // Current minimum value

        public MinStack_3() {
            this.stack = new Stack<>();
        }

        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = val;
            } else {
                long diff = (long) val - min;
                stack.push(diff);
                if (val < min) {
                    min = val;
                }
            }
        }

        public void pop() {
            if (stack.isEmpty()) return;
            
            long diff = stack.pop();
            // If diff is negative, it means this was the minimum element
            // and we need to restore the previous minimum
            if (diff < 0) {
                min = min - diff; // Restore previous min
            }
        }

        public int top() {
            long diff = stack.peek();
            // If diff is negative, current element is the minimum
            if (diff < 0) {
                return (int) min;
            }
            // Otherwise, actual value = min + diff
            return (int) (min + diff);
        }

        public int getMin() {
            return (int) min;
        }
    }

    /**
     * Approach 4: Linked List with Min Tracking (Most Space Efficient for Sparse Operations)
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n)
     * 
     * Optimization: Each node stores its value and the minimum seen so far
     * No need for separate stack structure, just linked list nodes
     */
    static class MinStack_4 {
        private Node head;

        private static class Node {
            int val;
            int min;
            Node next;

            Node(int val, int min, Node next) {
                this.val = val;
                this.min = min;
                this.next = next;
            }
        }

        public MinStack_4() {
            // head is null initially
        }

        public void push(int val) {
            if (head == null) {
                head = new Node(val, val, null);
            } else {
                head = new Node(val, Math.min(val, head.min), head);
            }
        }

        public void pop() {
            if (head != null) {
                head = head.next;
            }
        }

        public int top() {
            return head.val;
        }

        public int getMin() {
            return head.min;
        }
    }

    /**
     * Approach 5: Two Stacks with Deque (Original approach, optimized)
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n)
     * 
     * Optimization: Using Deque for better performance, cleaner code
     */
    static class MinStack_5 {
        private Deque<Integer> stack;
        private Deque<Integer> minStack;

        public MinStack_5() {
            this.stack = new LinkedList<>();
            this.minStack = new LinkedList<>();
        }

        public void push(int val) {
            stack.push(val);
            // Optimized: Only push when val is <= current min (handles duplicates)
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            // LeetCode guarantees valid operations
            int removedValue = stack.pop();
            // Only pop if the removed value was the minimum
            if (removedValue == minStack.peek()) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
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
