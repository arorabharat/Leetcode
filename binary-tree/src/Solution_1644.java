/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-ii/
 * https://leetcode.jp/problemdetail.php?id=1644
 * Lowest common ancestor special case where number of nodes = 2 and no other conditions
 */
class Solution_1644 {

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

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return _lowestCommonAncestor(root, p, q).ancestor;
    }

}