/**
 * https://leetcode.com/problems/linked-list-cycle/
 */
class Solution_141 {

    /**
     * if the linked list has less than two nodes than there could not be a cycle
     * We will use floyds two pointer approach.
     * Fast pointer will jump two steps every time and slow pointer will jump on step every time
     * if both of them intersect at some point then there is a cycle
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) return true;
        }
        return false;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}