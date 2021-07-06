/**
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 */
class Solution_19 {

    /**
     * Maintain two pointer at a distance of N : pointer and pointerPlusN
     * increment them at the same time if the pointerPlusN would have reached last node.
     * then pointer would be at N+1 from last node and we could simply delete the node using the pointer.
     * because n could be equal to the size of the list, we need to create a pseudo node
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pseudo = new ListNode(-1);
        pseudo.next = head;
        ListNode pointer = pseudo;
        ListNode pointerPlusN = pointer;
        int count = 0;
        while (pointerPlusN != null && count < n) {
            pointerPlusN = pointerPlusN.next;
            count++;
        }
        if (pointerPlusN == null) {
            // nth node from the last does not exist
            return pseudo.next;
        }
        while (pointerPlusN.next != null) {
            pointer = pointer.next;
            pointerPlusN = pointerPlusN.next;
        }
        pointer.next = pointer.next.next;
        return pseudo.next;
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