/**
 * https://leetcode.com/problems/swap-nodes-in-pairs/
 */
class Solution_24 {

    public class ListNode {
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

    ListNode swapFirstPair(ListNode prev, ListNode a, ListNode b) {
        prev.next = b;
        a.next = b.next;
        b.next = a;
        return prev.next;
    }

    public ListNode swapPairs(ListNode head) {
        // pseudo node in linked list
        ListNode ans = new ListNode(0);
        ans.next = head;
        ListNode tr = head;
        ListNode prev = ans;
        while (tr != null && tr.next != null) {
            tr = swapFirstPair(prev, tr, tr.next);
            prev = tr.next;
            tr = tr.next.next;

        }

        return ans.next;
    }
}