import java.util.List;

/**
 * https://leetcode.com/problems/all-possible-full-binary-trees/
 * TODO : tough
 */
class Solution_894 {

    public List<TreeNode> allPossibleFBT(int n) {
        return null;
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