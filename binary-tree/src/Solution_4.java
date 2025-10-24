public class Solution_4 {

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

    class Approach_1 {

        public boolean isSameTree(TreeNode r1, TreeNode r2) {
            if (r1 == null && r2 == null) return true;
            if (r1 == null || r2 == null) return false;
            if (r1.val != r2.val) return false;
            return isSameTree(r1.left, r2.left) && isSameTree(r1.right, r2.right);
        }

        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            if (root == null || subRoot == null) return false;
            return isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
        }
    }
}
