/**
 * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/description/
 */
public class Solution_108 {

    private TreeNode _sortedArrayToBST(int[] nums, int start, int end) {
        if (start > end) return null;
        int mid = (start + end + 1) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = _sortedArrayToBST(nums, start, mid - 1);
        root.right = _sortedArrayToBST(nums, mid + 1, end);
        return root;
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        int n = nums.length;
        return _sortedArrayToBST(nums, 0, n - 1);
    }

    public static class TreeNode {
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
