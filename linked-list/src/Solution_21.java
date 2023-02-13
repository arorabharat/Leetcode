/**
 * https://leetcode.com/problems/merge-two-sorted-lists/
 */
class Solution_21 {

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
     * Re-Attempt Tue Feb 14 01:11:06 IST 2023 and code was improved
     */
    public ListNode mergeTwoLists3(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode tr1 = list1;
        ListNode tr2 = list2;
        ListNode result = new ListNode();
        ListNode tr = result;
        while (tr1 != null && tr2 != null) {
            if (tr1.val < tr2.val) {
                tr.next = tr1;
                tr1 = removeNextPointerAndMoveNext(tr1);
            } else {
                tr.next = tr2;
                tr2 = removeNextPointerAndMoveNext(tr2);
            }
            tr = tr.next;
        }
        if (tr1 == null) {
            tr.next = tr2;
        }
        if (tr2 == null) {
            tr.next = tr1;
        }
        return removeNextPointerAndMoveNext(result);
    }

    private ListNode removeNextPointerAndMoveNext(ListNode tr) {
        ListNode next = tr.next;
        tr.next = null;
        return next;
    }
}