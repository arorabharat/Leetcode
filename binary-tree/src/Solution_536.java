/**
 * https://leetcode.com/problems/construct-binary-tree-from-string/
 * TODO : important
 */
class Solution_536 {

    /**
     * @see DSA#TREE_DESERIALIZATION
     */
    public TreeNode str2tree(String s) {

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