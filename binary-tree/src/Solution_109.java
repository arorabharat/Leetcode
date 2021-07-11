/**
 * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
 * TODO
 */
class Solution_109 {

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

    ListNode getMidNode(ListNode start) {
        ListNode slow = new ListNode(-1);
        slow.next = start;
        ListNode fast = slow;
        ListNode prev = slow;
        while (fast != null && fast.next != null && slow != null) {
            fast = fast.next.next;
            prev = slow;
            slow = slow.next;

        }
        return fast == null ? prev : prev.next;
    }

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);
        ListNode leftNode = getMidNode(head);
        ListNode rootNode = leftNode.next;
        ListNode rightNode = leftNode.next.next;
        rootNode.next = null;
        leftNode.next = null;
        TreeNode root = new TreeNode(rootNode.val);
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(rightNode);
        return root;
    }
    
}