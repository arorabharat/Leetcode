/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
 */
class Solution_82 {

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
     * take three pointer - prev, curr and next
     * invariant -
     * 1. prev will point to the last unique value encountered
     * curr will point to next value for processing and next would try to point to next value which is not equal to current
     * if the value is not unique we do not need to update the prev pointer but if the value is unique then we need to update the prev pointer
     * if next is pointing to a non null value we will keep doing the same process
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = new ListNode(-101);
        prev.next = head;
        ListNode curr = head;
        ListNode next = head.next;
        ListNode start = prev;
        while (next != null) {
            boolean isUnique = true;
            while (next != null && next.val == curr.val) {
                next = next.next;
                isUnique = false;
            }
            if (isUnique) {
                prev = curr;
            } else {
                prev.next = next;
            }
            if (next != null) {
                curr = next;
                next = next.next;
            }
        }
        return start.next;
    }
}