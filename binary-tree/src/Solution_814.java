public class Solution_814 {

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

    public boolean _pruneTree(TreeNode root) {
        if (root != null) {
            boolean left = _pruneTree(root.left);
            boolean right = _pruneTree(root.right);
            if (!left) {
                root.left = null;
            }
            if (!right) {
                root.right = null;
            }
            return root.val == 1 || left || right;
        }
        return false;
    }

    public TreeNode pruneTree(TreeNode root) {
        boolean r = _pruneTree(root);
        return (r) ? root : null;
    }
}