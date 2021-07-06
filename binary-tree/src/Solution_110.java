import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/balanced-binary-tree/
 *
 * @see Solution_104
 */
class Solution_110 {

    private final Map<TreeNode, Integer> cache = new HashMap<>();

    private int height(TreeNode root) {
        if (root == null) return 0;
        if (cache.containsKey(root)) return cache.get(root);
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        int h = Math.max(leftHeight, rightHeight) + 1;
        cache.put(root, h);
        return h;
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int heightDiff = Math.abs(height(root.left) - height(root.right));
        return heightDiff <= 1 && isBalanced(root.right) && isBalanced(root.left);
    }

    /**
     * Another approach similar to above -
     * Fact : if the one of the child is unbalanced then node can not be balanced.
     * So we will recursively traverse the root ad store height if only tree is balance otherwise we will just store null
     */
    private TreeInfo isBalancedTreeHelper(TreeNode root) {
        if (root == null) {
            return new TreeInfo(0, true);
        }
        TreeInfo left = isBalancedTreeHelper(root.left);
        if (!left.balanced) {
            return new TreeInfo(null, false);
        }
        TreeInfo right = isBalancedTreeHelper(root.right);
        if (!right.balanced) {
            return new TreeInfo(null, false);
        }
        if (Math.abs(left.height - right.height) <= 1) {
            return new TreeInfo(Math.max(left.height, right.height) + 1, true);
        }
        return new TreeInfo(null, false);
    }

    public boolean isBalanced2(TreeNode root) {
        return isBalancedTreeHelper(root).balanced;
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

    static class TreeInfo {
        public final Integer height;
        public final boolean balanced;

        public TreeInfo(Integer height, boolean balanced) {
            this.height = height;
            this.balanced = balanced;
        }
    }

}