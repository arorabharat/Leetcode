import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-postorder-traversal/
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

    /**
     * ================== PostOrder / Post Order Traversal ===============================
     * Recursive
     */
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

    /**
     * Iterative
     */
    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> postOrderList = new ArrayList<>();
        if (root == null) return new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        Set<TreeNode> visited = new HashSet<>();
        visited.add(root);
        while (!stack.isEmpty()) {
            TreeNode front = stack.pop();
            if ( front.left != null && !visited.contains(front.left)){
                stack.add(front.left);
                visited.add(front.left);
            }
            if (front.right != null && !visited.contains(front.right)){
                stack.add(front.right);
                visited.add(front.right);
            }
            postOrderList.add(front.val);
        }
        Collections.reverse(postOrderList);
        return postOrderList;
    }
}