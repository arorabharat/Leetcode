/**
 * https://leetcode.com/problems/rotate-list/
 */
class Solution_61 {

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

    /**
     * Every time in the while loop it encounter non null node - it increments the counter
     */
    private int countNodes(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    /**
     * To solve the problem basically we need k+1 node from the end.
     * Maintain two pointer at distance of K : pointer and pointerPlusK
     * Increment both the pointer at the same time.
     * k = [0,n-1]
     */
    public ListNode rotateRight(ListNode head, int k) {
        int n = countNodes(head);
        if (n == 0) return head;
        k = k % n;
        if (k == 0) return head;
        ListNode pointer = head;
        ListNode pointerPlusK = head;
        int count = 0;
        while (pointerPlusK != null && count < k) {
            pointerPlusK = pointerPlusK.next;
            count++;
        }
        while (pointerPlusK.next != null) {
            pointerPlusK = pointerPlusK.next;
            pointer = pointer.next;
        }
        ListNode start = pointer.next; // pointer head to kth node from the last
        pointer.next = null; // break the link to kth node
        pointerPlusK.next = head; // point last node next to head
        return start;
    }
}