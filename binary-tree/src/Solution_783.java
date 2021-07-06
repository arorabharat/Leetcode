/**
 * https://leetcode.com/problems/minimum-distance-between-bst-nodes/
 *
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

    Integer prev, ans;

    public int minDiffInBST1(TreeNode root) {
        prev = null;
        ans = Integer.MAX_VALUE;
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode node) {
        if (node == null) return;
        dfs(node.left);
        if (prev != null)
            ans = Math.min(ans, node.val - prev);
        prev = node.val;
        dfs(node.right);
    }
}