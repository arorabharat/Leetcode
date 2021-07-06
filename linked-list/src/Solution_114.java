/**
 * https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
 */
class Solution_114 {
    /**
     * This method flats the tree at given node.
     * For every node we are going to flat the left subtree than make it the right child of the node .
     * Then we are going to flat the right sub tree and add  as right child  to the last node of the flatten left subtree.
     */
    private TreeNode _flatten(TreeNode root) {
        if (root != null) {
            TreeNode leftSubTree = root.left;
            TreeNode rightSubTree = root.right;
            root.left = null;
            root.right = _flatten(leftSubTree);
            TreeNode flatLeftSubTree = root;
            while (flatLeftSubTree.right != null) {
                flatLeftSubTree = flatLeftSubTree.right;
            }
            flatLeftSubTree.right = _flatten(rightSubTree);
        }
        return root;
    }

    public void flatten(TreeNode root) {
        _flatten(root);
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