import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-preorder-traversal/
 */
class Solution_144 {
    /**
     * ======================== PreOrder / Pre order ==================================
     * Recursive Solution
     * Time complexity : O(N)
     * Space Complexity : O(N) Stack space for running the algo
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> preOrderList = new ArrayList<>();
        _preorderTraversal(root, preOrderList);
        return preOrderList;
    }

    private void _preorderTraversal(TreeNode root, List<Integer> preOrderList) {
        if (root != null) {
            preOrderList.add(root.val);
            _preorderTraversal(root.left, preOrderList);
            _preorderTraversal(root.right, preOrderList);
        }
    }

    /**
     * Iterative Solution :
     * V - L - R
     * Trick is to add the nodes in reverse pre oder in stack
     * Time complexity : O(N)
     * Space Complexity : O(N)
     */
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> preOrderList = new ArrayList<>();
        if (root == null) return preOrderList;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode front = stack.pop();
            preOrderList.add(front.val);
            if (front.right != null) {
                stack.push(front.right);
            }
            if (front.left != null) {
                stack.push(front.left);
            }
        }
        return preOrderList;
    }

    /**
     * Iterative solution using deque.
     * V - L - R
     * once the node is in traversal data structure ( stack / deque ) never put the node back inside in the data structure, this invariant should hold for any traversal.
     * Traverse the node and then add its right child first then the left child.
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> preOrderList = new ArrayList<>();
        if (root == null) return preOrderList;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        while (!deque.isEmpty()) {
            TreeNode front = deque.remove();
            preOrderList.add(front.val);
            if (front.right != null) {
                deque.addFirst(front.right);
            }
            if (front.left != null) {
                deque.addFirst(front.left);
            }
        }
        return preOrderList;
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