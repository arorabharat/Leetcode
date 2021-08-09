import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/
 */
class Solution_430 {

    public Node flatten(Node head) {
        if (head == null) return null;
        Node next = head.next;
        head.next = null;
        Node curr = head;
        Node child = head.child;
        head.child = null;
        if (child != null) {
            child = flatten(child);
            curr.next = child;
            child.prev = curr;
        }
        while (curr.next != null) {
            curr = curr.next;
        }
        if (next != null) {
            next = flatten(next);
            curr.next = next;
            next.prev = curr;
        }
        return head;
    }

    /**
     * iterative approach
     * we add the next and child element in the stack
     * keep linking the curr / top element of hte stack to the prev element / element last added to the final list
     */
    public Node flatten2(Node head) {
        if (head == null) return head;
        Node pseudoHead = new Node(0, null, head, null);
        Node curr = null;
        Node finalList = pseudoHead;
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            curr = stack.pop();
            finalList.next = curr;
            curr.prev = finalList;
            if (curr.next != null) {
                stack.push(curr.next);
            }
            if (curr.child != null) {
                stack.push(curr.child);
                curr.child = null;
            }
            finalList = curr;
        }
        // detach the pseudo node from the result
        pseudoHead.next.prev = null;
        return pseudoHead.next;
    }

    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;

        public Node(int val, Node prev, Node next, Node child) {
            this.val = val;
            this.prev = prev;
            this.next = next;
            this.child = child;
        }
    }
}