/**
 * https://leetcode.com/problems/intersection-of-two-linked-lists/
 * slow and fact pointer
 * floyd's cycle detection
 */
class Solution_160 {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    private ListNode getLastNode(ListNode head) {
        if (head == null) {
            return null;
        }
        while (head.next != null) {
            head = head.next;
        }
        return head;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode lastNode = getLastNode(headA);
        lastNode.next = headA;
        ListNode fast = headB;
        ListNode slow = headB;
        boolean firstTime = true;
        while (fast != null && fast.next != null && slow != null && (fast != slow || firstTime)) {
            fast = fast.next.next;
            slow = slow.next;
            firstTime = false;
        }
        // there is no intersection
        if (fast == null || fast.next == null) {
            lastNode.next = null;
            return null;
        }
        ListNode slow2 = headB;
        while (slow != null && slow2 != null && slow != slow2) {
            slow = slow.next;
            slow2 = slow2.next;
        }
        // revert the lastNode to headA pointer
        lastNode.next = null;
        return slow2;
    }

    private int countNode(ListNode head) {
        int c = 0;
        while (head != null) {
            c++;
            head = head.next;
        }
        return c;
    }

    private ListNode moveForwardByCount(ListNode head, int count) {
        int c = 0;
        while (head != null && c < count) {
            c++;
            head = head.next;
        }
        return head;
    }

    /**
     * Approach 2: shift the pointer of long list by the difference in the list length
     * then run the two pointer parallely to find the intersection
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int na = countNode(headA);
        int nb = countNode(headB);
        int shiftCount;
        ListNode shortList;
        ListNode longList;
        if (na > nb) {
            shiftCount = na - nb;
            longList = headA;
            shortList = headB;
        } else {
            shiftCount = nb - na;
            longList = headB;
            shortList = headA;
        }
        ListNode shiftedLongList = moveForwardByCount(longList, shiftCount);
        while (shiftedLongList != null && shortList != null && shiftedLongList != shortList) {
            shiftedLongList = shiftedLongList.next;
            shortList = shortList.next;
        }
        return shiftedLongList != null && shortList != null ? shiftedLongList : null;
    }
}