/**
 * https://leetcode.com/problems/palindrome-linked-list/
 */
class Solution_234 {

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

    private int countNode(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    private ListNode reverse(ListNode head) {
        if (head == null) return null;
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

    private boolean prefixMatch(ListNode list1, ListNode list2) {
        while (list1 != null && list2 != null) {
            if (list1.val != list2.val) {
                return false;
            }
            list1 = list1.next;
            list2 = list2.next;
        }
        return true;
    }

    private ListNode moveForwardByCount(ListNode list, int count) {
        int c = 0;
        while (list != null && c < count) {
            c++;
            list = list.next;
        }
        return list;
    }

    public boolean isPalindrome(ListNode head) {
        int n = countNode(head);
        if (n < 2) {
            return true;
        } else if (n == 2) {
            return head.val == head.next.val;
        } else {
            int m = (n + 1) / 2 - 1;
            ListNode leftMedian = moveForwardByCount(head, m);
            // break the linked list from mid and reverse
            ListNode right = leftMedian.next;
            leftMedian.next = null;
            right = reverse(right);
            boolean isPalindrome = prefixMatch(head, right);
            // reverse and join again
            right = reverse(right);
            leftMedian.next = right;
            return isPalindrome;
        }
    }
}