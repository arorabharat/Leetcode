/**
 * https://leetcode.com/problems/add-two-numbers/
 */
class Solution_2 {

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

    // add two numbers in the linked list
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode tr1 = l1;
        ListNode tr2 = l2;
        ListNode head = null;
        ListNode res = null;
        int c = 0;

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
            int sum = a + b + c;
            c = sum / 10;
            sum = sum % 10;
            ListNode t = new ListNode(sum);
            if (head == null) {
                head = t;
                res = head;
            } else {
                res.next = t;
                res = res.next;
            }
        }
        if (c != 0) {
            res.next = new ListNode(c);
        }
        return head;
    }
}
