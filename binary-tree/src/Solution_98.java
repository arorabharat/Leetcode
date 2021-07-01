/**
 * https://leetcode.com/problems/balanced-binary-tree/
 *
 * @see Solution_104
 */
class Solution_98 {

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

    private boolean inRange(long x, long min, long max) {
        return min <= x && x <= max;
    }

    private boolean _isValidBST(TreeNode root, long min, long max) {
        if (root == null) return true;
        return inRange(root.val, min, max)
                && _isValidBST(root.left, min, (long) root.val - 1)
                && _isValidBST(root.right, (long) root.val + 1, max);
    }

    public boolean isValidBST(TreeNode root) {
        return _isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }


    /**
     * second approach
     */
    public boolean validate(TreeNode root, Integer low, Integer high) {
        // Empty trees are valid BSTs.
        if (root == null) {
            return true;
        }
        // The current node's value must be between low and high.
        if ((low != null && root.val <= low) || (high != null && root.val >= high)) {
            return false;
        }
        // The left and right subtree must also be valid.
        return validate(root.right, root.val, high) && validate(root.left, low, root.val);
    }

    public boolean isValidBST2(TreeNode root) {
        return validate(root, null, null);
    }

    /**
     * third approach
     */
    private Integer prev;

    public boolean isValidBST3(TreeNode root) {
        prev = null;
        return inorder(root);
    }

    private boolean inorder(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!inorder(root.left)) {
            return false;
        }
        if (prev != null && root.val <= prev) {
            return false;
        }
        prev = root.val;
        return inorder(root.right);
    }
}