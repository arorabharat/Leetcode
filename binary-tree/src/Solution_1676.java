/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iv/
 *
 * Lowest common ancestor  with number of nodes for which ancestor need to be calculated = N
 * keys are unique and keys exist in the node. So it will be similar to shortcut taken in 1644.
 * But the generic implementation is better
 */
class Solution_1676 {

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

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
        return _lowestCommonAncestor(root, nodes).ancestor;
    }

}
