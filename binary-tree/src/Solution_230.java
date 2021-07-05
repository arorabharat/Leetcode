import java.util.Optional;

/**
 * https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 */
class Solution_230 {

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

    int nodeCount = 0;

    private Optional<TreeNode> inorder(TreeNode root, int k) {
        if (root != null) {
            Optional<TreeNode> result = inorder(root.left, k);
            if (result.isPresent()) return result;
            nodeCount++;
            if (nodeCount == k) {
                return Optional.of(root);
            }
            return inorder(root.right, k);
        }
        return Optional.empty();
    }

    public int kthSmallest(TreeNode root, int k) {
        this.nodeCount = 0;
        Optional<TreeNode> result = inorder(root, k);
        return result.map(treeNode -> treeNode.val).orElse(Integer.MIN_VALUE);
    }
}