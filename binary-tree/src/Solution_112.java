/**
 * https://leetcode.com/problems/path-sum/
 *
 * @see Solution_113
 * @see Solution_124
 * @see Solution_129
 * @see Solution_437
 */
class Solution_112 {

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
}
