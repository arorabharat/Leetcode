/**
 * https://www.youtube.com/watch?v=Osz-Vwer6rw&list=PL_z_8CaSLPWekqhdCPmFohncHwz8TY2Go&index=49
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/
 */
class Solution_124 {

    int sum;

    /**
     * Max path sum is defined as the sum of nodes values on any path on the tree ( where path is defined between any source and destination node ).
     * It is not necessary for source and destination to be leaf node like diameter
     * we calculate maxPathSumInSubTreeFromRootNode() for left and right child,
     * Using this we could calculate the max sum path for each node , assuming path pass through root. Take maximum of those path to get the answer
     */
    private int maxPathSumInSubTreeFromRootNode(TreeNode root) {
        if (root == null) return 0;
        int ls = maxPathSumInSubTreeFromRootNode(root.left);
        int rs = maxPathSumInSubTreeFromRootNode(root.right);
        sum = Math.max(sum, ls + rs + root.val);
        int maxPathSumInSubtreeFromRoot = Math.max(ls, rs) + root.val;
        return Math.max(0, maxPathSumInSubtreeFromRoot); // because source and destination not necessary to leaf we could reject the sum if it is not adding value.
    }

    public int maxPathSum(TreeNode root) {
        if (root == null) return 0;
        sum = Integer.MIN_VALUE;
        maxPathSumInSubTreeFromRootNode(root);
        return sum;
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