/**
 * https://leetcode.com/problems/minimum-distance-between-bst-nodes/
 * TODO : read more on this
 * @see Solution_366
 * @see Solution_549
 * @see Solution_1485
 */
class Solution_783 {

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

    int minDifference = Integer.MAX_VALUE;

    private TreeNode inOrder(TreeNode root, TreeNode lastTraverseNode) {
        if (root != null) {
            if (root.left != null) {
                TreeNode leftNode = inOrder(root.left, lastTraverseNode);
                lastTraverseNode = leftNode != null ? leftNode : lastTraverseNode;
            }
            if (lastTraverseNode != null) {
                int absDiff = root.val - lastTraverseNode.val;
                minDifference = Math.min(minDifference, absDiff);
            }
            lastTraverseNode = root;
            if (root.right != null) {
                TreeNode rightNode = inOrder(root.right, lastTraverseNode);
                lastTraverseNode = rightNode != null ? rightNode : lastTraverseNode;
            }
        }
        return lastTraverseNode;
    }

    public int minDiffInBST(TreeNode root) {
        minDifference = Integer.MAX_VALUE;
        inOrder(root, null);
        return minDifference;
    }
}