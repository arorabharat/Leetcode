/**
 * https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/
 */
class Solution_430 {

    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

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
}