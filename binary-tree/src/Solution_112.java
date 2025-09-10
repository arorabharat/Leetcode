import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/path-sum/
 *
 * @see Solution_113
 * @see Solution_124
 * @see Solution_129
 * @see Solution_437
 */
class Solution_112 {

    class Approach_1 {

        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) return false;
            Queue<Pair> q = new LinkedList<>();
            q.add(new Pair(root, targetSum));
            while (!q.isEmpty()) {
                Pair p = q.poll();
                TreeNode node = p.node;
                if (node.left == null && node.right == null && p.remSum == node.val) {
                    return true;
                }
                if (node.left != null) {
                    q.add(new Pair(node.left, p.remSum - node.val));
                }
                if (node.right != null) {
                    q.add(new Pair(node.right, p.remSum - node.val));
                }
            }
            return false;
        }

        static class Pair {
            TreeNode node;
            Integer remSum;

            public Pair(TreeNode node, Integer remSum) {
                this.node = node;
                this.remSum = remSum;
            }
        }
    }

    private boolean isLeafNode(TreeNode root) {
        return root != null && root.left == null && root.right == null;
    }

    /**
     * traverse through all the possible path one by one , at every step reduce the target sum by the current node value
     * keep going until you hit the leaf node with the value equal to the remaining target sum.
     * if we don't find such node and hit the root equals to null return false.
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.val == targetSum && isLeafNode(root)) return true;
        int remainingTargetSum = targetSum - root.val;
        return hasPathSum(root.left, remainingTargetSum) || hasPathSum(root.right, remainingTargetSum);
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
}
