import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/binary-tree-postorder-traversal/
 * TODO
 */
class Solution_145 {

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

    private void _postorderTraversal(TreeNode root, List<Integer> postOrderList) {
        if (root != null) {
            _postorderTraversal(root.left, postOrderList);
            _postorderTraversal(root.right, postOrderList);
            postOrderList.add(root.val);
        }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> postOrderList = new ArrayList<>();
        _postorderTraversal(root, postOrderList);
        return postOrderList;
    }
}