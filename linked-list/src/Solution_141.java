import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/linked-list-cycle/
 */
class Solution_141 {

    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    /**
     * brute force approach
     */
    public boolean hasCycle1(ListNode head) {
        Set<ListNode> nodesSeen = new HashSet<>();
        while (head != null) {
            if (nodesSeen.contains(head)) {
                return true;
            }
            nodesSeen.add(head);
            head = head.next;
        }
        return false;
    }

    /**
     * if the linked list has less than two nodes than there could not be a cycle
     * We will use floyds two pointer approach.
     * Fast pointer will jump two steps every time and slow pointer will jump on step every time
     * if both of them intersect at some point then there is a cycle
     *
     * @see DSA#FAST_AND_SLOW_POINTER
     */
    public boolean hasCycle2(ListNode head) {
        ListNode pseudo = new ListNode(-1);
        pseudo.next = head;
        ListNode slow = pseudo;
        ListNode fast = pseudo;
        boolean isCycle = false;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                isCycle = true;
                break;
            }
        }
        pseudo.next = null;
        return isCycle;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}