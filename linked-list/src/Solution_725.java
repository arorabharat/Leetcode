/**
 * https://leetcode.com/problems/split-linked-list-in-parts/
 */
class Solution_725 {

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

    int size(ListNode root) {
        int i = 0;
        ListNode tr = root;
        while (tr != null) {
            i++;
            tr = tr.next;
        }
        return i;
    }

    public ListNode[] splitListToParts(ListNode root, int k) {

        ListNode[] list = new ListNode[k];

        int s = size(root); // size of list
        int sb = s / k; // sublist size
        int r = s % k;
        ListNode start = root;
        for (int i = 0; i < k; i++) {
            list[i] = start;
            ListNode trb = start;
            ListNode tr = start;
            int j = 1;
            int sbr = sb;
            if (r > 0) {
                sbr = sb + 1;
                r--;
            }
            while (j < sbr && tr != null) {
                trb.next = tr.next;
                trb = trb.next;
                tr = tr.next;
                j++;
            }
            if (tr == null || tr.next == null) {
                break;
            }
            start = tr.next;
            tr.next = null;
        }
        return list;
    }
}