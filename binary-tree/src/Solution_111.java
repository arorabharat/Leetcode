import java.util.LinkedList;

/**
 * https://leetcode.com/problems/minimum-depth-of-binary-tree/
 *
 * @see Solution_102
 * @see Solution_104
 */
class Solution_111 {


    class Approach_1 {

        public int minDepth(TreeNode root) {
            if (root == null) return 0;
            if (root.left == null && root.right == null) {
                return 1;
            }
            if (root.left == null) {
                return minDepth(root.right) + 1;
            }
            if (root.right == null) {
                return minDepth(root.left) + 1;
            }
            return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
        }
    }


    private boolean isLeafNode(TreeNode root) {
        if (root == null) return false;
        return root.left == null && root.right == null;
    }

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (isLeafNode(root)) return 1;
        int min = Integer.MAX_VALUE;
        if (root.left != null) {
            min = Math.min(min, minDepth(root.left));
        }
        if (root.right != null) {
            min = Math.min(min, minDepth(root.right));
        }
        return min + 1;
    }

    /**
     * Using DFS
     */
    public int minDepth2(TreeNode root) {
        LinkedList<Pair<TreeNode, Integer>> stack = new LinkedList<>();
        if (root == null) {
            return 0;
        } else {
            stack.add(new Pair<>(root, 1));
        }

        int min_depth = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> current = stack.pollLast();
            root = current.getFirst();
            int current_depth = current.getSecond();
            if ((root.left == null) && (root.right == null)) {
                min_depth = Math.min(min_depth, current_depth);
            }
            if (root.left != null) {
                stack.add(new Pair<>(root.left, current_depth + 1));
            }
            if (root.right != null) {
                stack.add(new Pair<>(root.right, current_depth + 1));
            }
        }
        return min_depth;
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

    static class Pair<F, S> {
        F f;
        S s;

        public Pair(F f, S s) {
            this.f = f;
            this.s = s;
        }

        public F getFirst() {
            return f;
        }

        public S getSecond() {
            return s;
        }
    }
}