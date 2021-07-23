/**
 * https://leetcode.com/problems/add-two-numbers/
 */
class Solution_2 {

    // add two numbers in the linked list
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode tr1 = l1;
        ListNode tr2 = l2;
        ListNode head = null;
        ListNode tr3 = null;
        int carry = 0;

        while (tr1 != null || tr2 != null) {
            int a = 0;
            int b = 0;
            if (tr1 != null) {
                a = tr1.val;
                tr1 = tr1.next;
            }

            if (tr2 != null) {
                b = tr2.val;
                tr2 = tr2.next;
            }
            int sum = a + b + carry;
            carry = sum / 10;
            sum = sum % 10;
            ListNode t = new ListNode(sum);
            if (head == null) {
                head = t;
                tr3 = head;
            } else {
                tr3.next = t;
                tr3 = tr3.next;
            }
        }
        if (carry != 0) {
            tr3.next = new ListNode(carry);
        }
        return head;
    }


    /**
     * Optimizing the readability of approach 1
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode pseudoNode = new ListNode(0);
        ListNode tr1 = l1, tr2 = l2, tr3 = pseudoNode;
        int carry = 0;
        while (tr1 != null || tr2 != null) {
            int a = (tr1 != null) ? tr1.val : 0;
            int b = (tr2 != null) ? tr2.val : 0;
            int sum = carry + a + b;
            carry = sum / 10;
            tr3.next = new ListNode(sum % 10);
            tr3 = tr3.next;
            if (tr1 != null) tr1 = tr1.next;
            if (tr2 != null) tr2 = tr2.next;
        }
        if (carry > 0) {
            tr3.next = new ListNode(carry);
        }
        return pseudoNode.next;
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
