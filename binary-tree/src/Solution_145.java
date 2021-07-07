import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-postorder-traversal/
 */
class Solution_145 {

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
     * Stupid Iterative :
     * 1. once the node is in traversal data structure ( stack / deque ) never put the node back inside in the data strucutre
     * Above invariant should hold for any traversal.
     * Following solution is example of violation of this invariant
     *
     */
    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> postOrderList = new ArrayList<>();
        if (root == null) return new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        Set<TreeNode> visited = new HashSet<>();
        visited.add(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            if (current.left != null && !visited.contains(current.left)) {
                stack.add(current.left);
                visited.add(current.left);
            }
            if (current.right != null && !visited.contains(current.right)) {
                stack.add(current.right);
                visited.add(current.right);
            }
            postOrderList.add(current.val);
        }
        Collections.reverse(postOrderList);
        return postOrderList;
    }

    /**
     * Iterative solution using stack
     * 1. once the node is in traversal data structure ( stack / deque ) never put the node back inside in the data structure, this invariant should hold for any traversal.
     *  Following solution is adhere to this this invariant
     */
    public List<Integer> postorderTraversal2(TreeNode root) {
        Deque<Integer> queue = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.peek();
            stack.pop();
            queue.addFirst(curr.val);
            if (curr.left != null) {
                stack.push(curr.left);
            }
            if (curr.right != null) {
                stack.push(curr.right);
            }
        }
        return new ArrayList<>(queue);
    }

    /**
     * L - R - V
     * Iterative solution Deque.
     *
     */
    public List<Integer> postorderTraversal3(TreeNode root) {
        Deque<Integer> queue = new LinkedList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        if (root != null) deque.push(root);
        while (!deque.isEmpty()) {
            TreeNode curr = deque.peek();
            deque.pop();
            queue.addFirst(curr.val);
            if (curr.left != null) {
                deque.push(curr.left);
            }
            if (curr.right != null) {
                deque.push(curr.right);
            }
        }
        return new ArrayList<>(queue);
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