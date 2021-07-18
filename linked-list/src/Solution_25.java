/**
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 *
 */
class Solution_25 {

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

    ListNode reverse(ListNode head) {
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


    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) return null;
        ListNode tr = head;
        ListNode ansHead = null;
        ListNode ansTail = null;
        while (tr != null) {
            int i = 1;
            ListNode chunk = tr;
            while (tr.next != null && i < k) {
                tr = tr.next;
                i++;
            }
            ListNode rev = chunk;
            if (i == k) {
                ListNode temp = tr.next;
                tr.next = null;
                tr = temp;
                rev = reverse(chunk);
            } else {
                tr = null;
            }

            // append
            if (ansHead == null) {
                ansHead = rev;
                ansTail = rev;
            } else {
                ansTail.next = rev;
            }
            while (ansTail.next != null) {
                ansTail = ansTail.next;
            }
        }
        return ansHead;
    }
}