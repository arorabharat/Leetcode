/**
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 *
 * @see Solution_110
 * @see Solution_111
 * @see Solution_559
 * @see Solution_1376
 */
class Solution_104 {

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
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