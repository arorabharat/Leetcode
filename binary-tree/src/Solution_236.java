/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * Lowest common ancestor special case.
 * Where keys exits
 * Only two nodes
 * Keys are unique
 *
 * @see Solution_235
 */
class Solution_236 {

    private Pair _lowestCommonAncestor(TreeNode root, TreeNode... nodes) {
        int length = nodes.length;
        if (root == null) return new Pair(length);
        TreeNode left = root.left;
        TreeNode right = root.right;
        Pair leftResult = _lowestCommonAncestor(left, nodes);
        if (leftResult.ancestor != null) return leftResult;
        Pair rightResult = _lowestCommonAncestor(right, nodes);
        if (rightResult.ancestor != null) return rightResult;
        boolean[] nodesExists = new boolean[length];
        int countOfMatched = 0;
        for (int i = 0; i < length; i++) {
            if (!nodesExists[i]) {
                if (leftResult.nodesExists[i] || rightResult.nodesExists[i] || root.val == nodes[i].val) {
                    countOfMatched++;
                    nodesExists[i] = true;
                }
            }
        }
        TreeNode ancestor = (countOfMatched == length) ? root : null;
        return new Pair(ancestor, nodesExists);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return _lowestCommonAncestor(root, p, q).ancestor;
    }

    /**
     * This is approach seems easy but it has multiple flaws,
     * 1. it is not consistent in terms of method convention, lets say on root node
     * we are looking for common ancestor of 2 and 5 in the following tree and when we call method on the left subtree of root it return 5.
     * Which is not ancestor of the 2 and 5
     * 2
     * /
     * 5
     * 2. It does not work if the node does node exit in the tree.
     * we are looking for common ancestor of 2 and 5 in the following tree and when we call method on the left subtree of root it return 2.
     * Which is not ancestor of the 2 and 5, and further root will return 1 which is wrong.
     * 1
     * /
     * 2
     * /
     * 6
     * 3.It does not work if the node are not unique
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (p.val == root.val || q.val == root.val) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;

        return left == null ? right : left;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    static class Pair {
        TreeNode ancestor;
        boolean[] nodesExists;

        Pair(TreeNode ancestor, boolean[] nodesExists) {
            this.ancestor = ancestor;
            this.nodesExists = nodesExists;
        }

        Pair(int n) {
            this.ancestor = null;
            this.nodesExists = new boolean[n];
        }
    }

}