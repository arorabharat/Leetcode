/**
 * https://leetcode.com/problems/invert-binary-tree/
 */
class Solution_226 {

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

    private void swapChild(TreeNode root) {
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        swapChild(root);
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
}