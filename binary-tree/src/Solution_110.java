import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/balanced-binary-tree/
 *
 * @see Solution_104
 */
class Solution_110 {

    // basic recursive approach
    class Approach_1 {

        public int height(TreeNode root) {
            if (root == null) return 0;
            return Math.max(height(root.left), height(root.right)) + 1;
        }

        public boolean isBalanced(TreeNode root) {
            if (root == null) return true;
            int leftHeight = height(root.left);
            int rightHeight = height(root.right);
            return Math.abs(leftHeight - rightHeight) <= 1 &&
                    isBalanced(root.left) && isBalanced(root.right);
        }
    }

    // optimisation for overlapping subproblems
    class Approach_2 {

        private final Map<TreeNode, Integer> cache = new HashMap<>();

        public int height(TreeNode root) {
            if (root == null) return 0;
            if (cache.containsKey(root)) {
                return cache.get(root);
            }
            int leftHeight = height(root.left);
            int rightHeight = height(root.right);
            int rootHeight = Math.max(leftHeight, rightHeight) + 1;
            cache.put(root, rootHeight);
            return rootHeight;
        }

        public boolean isBalanced(TreeNode root) {
            if (root == null) return true;
            int leftHeight = height(root.left);
            int rightHeight = height(root.right);
            return Math.abs(leftHeight - rightHeight) <= 1 &&
                    isBalanced(root.left) && isBalanced(root.right);
        }
    }

    // early exit for optimisation, same as approach 2 otherwise
    class Approach_3 {

        public boolean isBalanced(TreeNode root) {
            return dfsHeight(root) != -1;
        }

        // Returns height if balanced; otherwise returns -1 as a sentinel.
        private int dfsHeight(TreeNode node) {
            if (node == null) return 0;

            int lh = dfsHeight(node.left);
            if (lh == -1) return -1;

            int rh = dfsHeight(node.right);
            if (rh == -1) return -1;

            if (Math.abs(lh - rh) > 1) return -1;

            return 1 + Math.max(lh, rh);             // height if balanced
        }
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