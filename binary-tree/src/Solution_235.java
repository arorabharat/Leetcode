/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 *
 * @see Solution_236
 */
class Solution_235 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * basically there are four case
     * Given p and q are unique.
     * root is equal to one of the node. as we have already traversed the above nodes and we know value exist in the subtree with current root, hence the current node will be LCS
     * both the nodes are greater than root, then we know common lowest ancestor is in right
     * both the nodes are smaller than root, then we know common lowest ancestor is on left
     * or both the values are on either side of the root. then current root is the lowest common ancestor.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root.val == p.val || root.val == q.val) return root;
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else {
            return root;
        }
    }
}