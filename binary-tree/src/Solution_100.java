import java.util.Objects;
import java.util.Stack;

/**
 * https://leetcode.com/problems/same-tree/
 */
class Solution_100 {

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

    /**
     * Iterative approach.
     * Do the pre order traversal and monitor the both the nodes in parallel.
     * Case 1 : both f and s are non null, then if there values not equal then tree is not same
     * Case 2 : one of the f and s is non null, that means tree not same
     * Case 3 : both the f and s are null then that means we are the bottom of the tree no further traversal required
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Stack<Pair<TreeNode, TreeNode>> stack = new Stack<>();
        stack.push(new Pair<>(p, q));
        while (!stack.empty()) {
            Pair<TreeNode, TreeNode> current = stack.pop();
            TreeNode f = current.getFirst();
            TreeNode s = current.getSecond();
            if (Objects.nonNull(f) && Objects.nonNull(s)) {
                if (f.val != s.val) {
                    return false;
                }
                stack.push(new Pair<>(f.left, s.left));
                stack.push(new Pair<>(f.right, s.right));
            } else if (Objects.nonNull(f) || Objects.nonNull(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Assume you keep one tree over the other,
     * Two tree are same if the current node have same value and left and right sub tree are same
     * Recursive approach
     */
    public boolean isSameTree2(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && isSameTree2(p.left, q.left) && isSameTree2(p.right, q.right);
    }
}