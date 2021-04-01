/**
 * https://leetcode.com/problems/diameter-of-binary-tree/
 */
class Solution_543 {

    private static class TreeNode {
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


    int height(TreeNode root) {
        if (root == null) return 0;
        int lh = height(root.left);
        int rh = height(root.right);
        return Math.max(lh, rh) + 1;
    }

    /**
     * Diameter is defined as maximum total number of edges between any two nodes
     * Case 1 : diameter passes through root.
     * Case 2 : diameter passes through some node in left sub tree
     * Case 3 : diameter passes through some node in right sub tree
     * Number of edges in the left sub tree in the deepest branch will be one less than height of the tree
     * diameter can be calculated : edges in deepest branch of left sub tree + edges in deepest branch of right sub tree +
     * ( edges connecting left sub tree and right sub tree )
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        int edgesLeft = height(root.left) - 1; // number of edges will be one less than height of the tree
        int edgesRight = height(root.right) - 1; // number of edges will be one less than height of the tree
        int rootHeight = edgesLeft + edgesRight + 2; // 2 edges which are connecting left and right child
        int left = diameterOfBinaryTree(root.left);
        int right = diameterOfBinaryTree(root.right);
        return Math.max(rootHeight, Math.max(left, right));
    }

    int diameter;

    /**
     * Optimised to avoid re-calculation of height
     */
    public int diameterOfBinaryTree2(TreeNode root) {
        diameter = 0;
        heightModified(root);
        return diameter - 1;
    }

    private int heightModified(TreeNode root) {
        if (root == null) return 0;
        int lh = heightModified(root.left);
        int rh = heightModified(root.right);
        diameter = Math.max(diameter, lh + rh + 1);
        return Math.max(lh, rh) + 1;
    }
}