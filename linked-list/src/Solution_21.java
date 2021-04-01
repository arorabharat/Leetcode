/**
 * https://leetcode.com/problems/merge-two-sorted-lists/
 */
class Solution_21 {

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
     * we will compare the front two values if they are non null.
     * Which one is smaller we will pick that.
     * Once one of the list is finished we will iterate over the rest of the elements of the other list
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode lastNode = null;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                if (lastNode == null) {
                    head = l1;
                    lastNode = head;
                } else {
                    lastNode.next = l1;
                    lastNode = l1;
                }
                l1 = l1.next;
            } else {
                if (lastNode == null) {
                    head = l2;
                    lastNode = head;
                } else {
                    lastNode.next = l2;
                    lastNode = l2;
                }
                l2 = l2.next;
            }
        }
        while (l1 != null) {
            if (lastNode == null) {
                head = l1;
                lastNode = head;
            } else {
                lastNode.next = l1;
                lastNode = l1;
            }
            l1 = l1.next;
        }
        while (l2 != null) {
            if (lastNode == null) {
                head = l2;
                lastNode = head;
            } else {
                lastNode.next = l2;
                lastNode = l2;
            }
            l2 = l2.next;
        }
        return head;
    }

    /**
     * Another approach which does not need if else to check if head is initialised
     * we will compare the front two values if they are non null.
     * Which one is smaller we will pick that.
     * Once one of the list is finished we will iterate over the rest of the elements of the other list
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode pseudoNode = new ListNode(0);
        ListNode lastNode = pseudoNode;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                lastNode.next = l1;
                lastNode = l1;
                l1 = l1.next;
            } else {
                lastNode.next = l2;
                lastNode = l2;
                l2 = l2.next;
            }
        }
        while (l1 != null) {
            lastNode.next = l1;
            lastNode = l1;
            l1 = l1.next;
        }
        while (l2 != null) {
            lastNode.next = l2;
            lastNode = l2;
            l2 = l2.next;
        }
        return pseudoNode.next;
    }
}