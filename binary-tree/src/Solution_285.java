/**
 * https://leetcode.com/problems/inorder-successor-in-bst/
 *
 * @see Solution_94
 * @see Solution_173
 * @see Solution_510
 */
class Solution_285 {

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) return null;
        if (root.left != null) {
            TreeNode successor = inorderSuccessor(root.left, p);
            if (successor != null) return successor;
        }
        if (root.val > p.val) {
            return root;
        }
        if (root.right != null) {
            return inorderSuccessor(root.right, p);
        }
        return null;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}