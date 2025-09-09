import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
 */
class Solution_109 {

    /**
     * Approach 1: convert the sorted list to array
     */

    // recursive approach
    class Approach_1 {

        private List<Integer> convertToArray(ListNode head) {
            final List<Integer> list = new ArrayList<>();
            ListNode curr = head;
            while (curr != null) {
                list.add(curr.val);
                curr = curr.next;
            }
            return list;
        }

        private TreeNode _listToBST(List<Integer> arr, final int s, final int e) {
            if (s > e) {
                return null;
            }
            if (s == e) {
                return new TreeNode(arr.get(s), null, null);
            }
            int m = s + (e - s + 1) / 2;
            TreeNode left = _listToBST(arr, s, m - 1);
            TreeNode right = _listToBST(arr, m + 1, e);
            return new TreeNode(arr.get(m), left, right);
        }

        public TreeNode sortedListToBST(ListNode head) {
            final List<Integer> list = convertToArray(head);
            return _listToBST(list, 0, list.size() - 1);
        }
    }

    class Approach_2 {

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
    }

    /**
     * Approach 3
     *
     * @see Level#GOOD
     */
    class Approach_3 {

        private ListNode head;

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