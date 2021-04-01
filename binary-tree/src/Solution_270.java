/**
 * https://leetcode.com/problems/closest-binary-search-tree-value/
 * https://leetcode.jp/problemdetail.php?id=270
 * TODO : Not verified
 */
class Solution_270 {

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

    public int closestValue(TreeNode root, double targetValue) {
        if (root == null) return Integer.MIN_VALUE;
        int subTreeValue;
        if (root.val == targetValue) {
            return root.val;
        } else if (root.val > targetValue) {
            subTreeValue = closestValue(root.left, targetValue);
        } else {
            subTreeValue = closestValue(root.right, targetValue);
        }
        if (Math.abs(targetValue - root.val) < Math.abs(targetValue - subTreeValue)) {
            return root.val;
        } else {
            return subTreeValue;
        }
    }
}
