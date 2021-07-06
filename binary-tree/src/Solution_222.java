/**
 * https://leetcode.com/problems/count-complete-tree-nodes/
 */
class Solution_222 {

    int getDepth(TreeNode root, Direction direction) {
        int count = 0;
        while (root != null) {
            count++;
            root = (direction == Direction.LEFT) ? root.left : root.right;
        }
        return count;
    }

    /**
     * If the tree is full binary tree than the number of nodes will be 2^h - 1.
     * If the tree is not full binary tree, than we will count the number of nodes in each node .
     * Total number of node will be = 1 + nodes in left + nodes in right
     * Time complexity : O(h * Log2(n) ) because the tree is complete either of child will be full, So in each iteration N will be reduced to half.
     * or O(h * Log2(n) ) = O(h^2)
     */
    public int countNodes(TreeNode root) {
        int leftDepth = getDepth(root, Direction.LEFT);
        int rightDepth = getDepth(root, Direction.RIGHT);
        if (rightDepth == leftDepth) {
            return (int) Math.pow(2, leftDepth) - 1;
        } else {
            return 1 + countNodes(root.left) + countNodes(root.right);
        }
    }

    enum Direction {
        LEFT, RIGHT
    }

    public class TreeNode {
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