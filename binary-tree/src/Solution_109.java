import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
 */
class Solution_109 {

    private ListNode head;

    /**
     * @see DSA#FAST_AND_SLOW_POINTER
     */
    ListNode getMidNode(ListNode head) {
        ListNode pseudo = new ListNode(-1);
        pseudo.next = head;
        ListNode fast = pseudo;
        ListNode slow = pseudo;
        ListNode prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            fast = fast.next.next;
            slow = slow.next;
        }
        return fast == null || prev == null ? prev : prev.next;
    }

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);
        ListNode leftTail = getMidNode(head);
        ListNode rootNode = leftTail.next;
        ListNode rightHead = leftTail.next.next;
        rootNode.next = null;
        leftTail.next = null;
        TreeNode root = new TreeNode(rootNode.val);
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(rightHead);
        return root;
    }

    /**
     * Approach 2 : convert the sorted list to array
     */


    private List<Integer> toArray(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode tr = head;
        while (tr != null) {
            list.add(tr.val);
            tr = tr.next;
        }
        return list;
    }

    private TreeNode _sortedArrayListToBST(List<Integer> list, int s, int e) {
        if (s <= e) {
            int m = (s + e) / 2;
            TreeNode root = new TreeNode(list.get(m));
            root.left = _sortedArrayListToBST(list, s, m - 1);
            root.right = _sortedArrayListToBST(list, m + 1, e);
            return root;
        }
        return null;
    }

    public TreeNode sortedListToBST1(ListNode head) {
        List<Integer> list = toArray(head);
        return _sortedArrayListToBST(list, 0, list.size() - 1);
    }

    /**
     * Approach 3
     *
     * @see Level#GOOD
     */

    private int getLength(ListNode head) {
        int count = 0;
        ListNode tr = head;
        while (tr != null) {
            count++;
            tr = tr.next;
        }
        return count;
    }

    private TreeNode inorderTraversal(int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        TreeNode left = inorderTraversal(start, mid - 1);
        TreeNode root = new TreeNode(head.val);
        root.left = left;
        head = head.next;
        root.right = inorderTraversal(mid + 1, end);
        return root;
    }

    public TreeNode sortedListToBST3(ListNode head) {
        this.head = head;
        int n = getLength(head);
        return inorderTraversal(0, n - 1);
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

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}