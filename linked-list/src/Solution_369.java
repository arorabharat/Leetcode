/**
 * https://leetcode.com/problems/plus-one-linked-list/
 */
class Solution_369 {

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
     * Reverse the linked list
     */
    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = head.next;
        while (next != null) {
            curr.next = prev;
            prev = curr;
            curr = next;
            next = next.next;
        }
        curr.next = prev;
        return curr;
    }

    public ListNode plusOne(ListNode head) {
        ListNode rev = reverse(head);
        ListNode plus = addOne(rev);
        return reverse(plus);
    }

    private ListNode addOne(ListNode head) {
        if (head == null) return new ListNode(1);
        int carry = 1;
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null && carry != 0) {
            int sum = curr.val + carry;
            carry = sum / 10;
            curr.val = sum % 10;
            prev = curr;
            curr = curr.next;
        }
        if (carry > 0) {
            prev.next = new ListNode(carry);
        }
        return head;
    }
}