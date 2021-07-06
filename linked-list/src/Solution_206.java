/**
 * https://leetcode.com/problems/reverse-linked-list/
 */
class Solution_206 {
    /**
     * Take three pointers:
     * invariant :
     * prev will point to the head of the reversed list
     * curr will point to the current node we are processing ( we reverse the pointer of current node ) ( head of the unprocessed list)
     * next will point to next node which need to reversed once the current is processed.
     * H
     * null 1->2->3
     * p  c n
     * <p>
     * H
     * null<-1 2->3
     * p  c n
     * <p>
     * H
     * null<-1 2->3
     * p c  n
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) return null;
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = head.next;
        while (next != null) {
            curr.next = prev; // reverse the pointer
            prev = curr;
            curr = next;
            next = next.next;
        }
        curr.next = prev;
        return curr;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}