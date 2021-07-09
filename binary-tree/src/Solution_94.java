import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-inorder-traversal/
 *
 * @see Solution_173
 * @see Solution_272
 * @see Solution_285
 * @see Solution_783
 * @see Solution_426
 * @see Solution_98
 * @see Solution_144
 * @see Solution_145
 * @see Solution_230
 */
class Solution_94 {

    public void addLeft(TreeNode tr, Stack<TreeNode> stack) {
        while (tr != null) {
            stack.add(tr);
            tr = tr.left;
        }
    }

    /**
     * @see DSA#IN_ORDER_TRAVERSAL
     * inorder traversal using the iterative approach
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        addLeft(root, stack);
        while (!stack.isEmpty()) {
            TreeNode f = stack.pop();
            list.add(f.val);
            if (f.right != null) {
                addLeft(f.right, stack);
            }
        }
        return list;
    }

    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        _inorderTraversal1(root, list);
        return list;
    }

    /**
     * Inorder traversal using recursion
     * Time complexity : O(N)
     * Space Complexity : O(N)
     */
    public void _inorderTraversal1(TreeNode root, List<Integer> list) {
        if (root != null) {
            _inorderTraversal1(root.left, list);
            list.add(root.val);
            _inorderTraversal1(root.right, list);
        }
    }

    public void addLeft(TreeNode tr, Deque<TreeNode> deque) {
        while (tr != null) {
            deque.addFirst(tr);
            tr = tr.left;
        }
    }

    /**
     * Two invariant :
     * all the nodes should be only added to the data structure once .
     * Order of node should reverse of the nodes required.
     */
    public List<Integer> inorderTraversal3(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        addLeft(root, deque);
        while (!deque.isEmpty()) {
            TreeNode f = deque.pop();
            list.add(f.val);
            if (f.right != null) {
                addLeft(f.right, deque);
            }
        }
        return list;
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
