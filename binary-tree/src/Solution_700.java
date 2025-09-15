import java.util.Stack;

/**
 * https://leetcode.com/problems/search-in-a-binary-search-tree/
 */
public class Solution_700 {

    // pre order traversal, recursive
    class Approach_1 {
        public TreeNode searchBST(TreeNode root, int val) {
            if (root == null) return null;
            if (root.val == val) return root;
            TreeNode leftSearch = searchBST(root.left, val);
            if (leftSearch != null) {
                return leftSearch;
            }
            return searchBST(root.right, val);
        }
    }

    // iterative using DFS
    class Approach_2 {

        public TreeNode searchBST(TreeNode root, int val) {
            Stack<TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode f = stack.pop();
                if (f.val == val) {
                    return f;
                }
                if (f.right != null) {
                    stack.add(f.right);
                }
                if (f.left != null) {
                    stack.add(f.left);
                }
            }
            return null;
        }
    }

    // leveraging BST property
    class Approach_3 {
        public TreeNode searchBST(TreeNode root, int val) {
            TreeNode it = root;
            while (it != null) {
                if (it.val < val) {
                    it = it.right;
                } else if (it.val == val) {
                    return it;
                } else {
                    it = it.left;
                }
            }
            return null;
        }
    }

    public class TreeNode {
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
