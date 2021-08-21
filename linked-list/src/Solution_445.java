/**
 * https://leetcode.com/problems/add-two-numbers-ii/
 */
class Solution_445 {

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

    public ListNode reverse(ListNode head) {
        if (head.next == null) return head;
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

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverse(l1);
        l2 = reverse(l2);

        int carry = 0;
        ListNode tr1 = l1;
        ListNode tr2 = l2;
        ListNode result = new ListNode();
        ListNode tr3 = result;
        while (tr1 != null || tr2 != null) {
            int a = 0;
            if (tr1 != null) {
                a = tr1.val;
                tr1 = tr1.next;
            }
            int b = 0;
            if (tr2 != null) {
                b = tr2.val;
                tr2 = tr2.next;
            }
            int sum = a + b + carry;
            carry = sum / 10;
            sum = sum % 10;
            tr3.next = new ListNode(sum);
            tr3 = tr3.next;
        }
        if (carry != 0) {
            tr3.next = new ListNode(carry);
            tr3 = tr3.next;
        }
        return reverse(result.next);
    }
}