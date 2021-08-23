/**
 * https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/
 */
class Solution_708 {

    static class Node {
        public int val;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }

    /**
     * It returns the index of the node after which node need to be inserted
     * <p>
     * Insert 2 in the [1 ,1, 1, 1, 1] (equal case)
     * Insert 2 in the [12, 13, 14, 10, 11 ] (maxValue node case)
     * Insert 3 in the [6, 8, 10, 2 , 4] (tr.val < v)
     * Insert 7 in the [6, 8, 10, 2 , 4] (tr.val < v and index.val > tr.val )
     */
    private Node getInsertionNodeIndex(Node head, int v) {
        Node tr = head;
        Node index = null;
        Node maxValueNode = new Node(Integer.MIN_VALUE);
        do {
            if (tr.val < v) {
                index = (index != null && index.val > tr.val) ? index : tr;
            } else if (tr.val == v) {
                index = tr;
            }
            if (maxValueNode.val < tr.val) {
                maxValueNode = tr;
            }
            tr = tr.next;
        } while (tr != head);
        return index != null ? index : maxValueNode;
    }


    public Node insert(Node head, int v) {
        if (head == null) {
            head = new Node(v);
            head.next = head;
            return head;
        }
        Node insertionIndex = getInsertionNodeIndex(head, v);
        Node next = insertionIndex.next;
        insertionIndex.next = new Node(v);
        insertionIndex = insertionIndex.next;
        insertionIndex.next = next;
        return head;
    }
}