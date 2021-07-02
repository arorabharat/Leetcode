import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/binary-tree-level-order-traversal/
 * @see Solution_103
 * @see Solution_107
 * @see Solution_111
 * @see Solution_314
 * @see Solution_429
 * @see Solution_637
 * @see Solution_993
 */
class Solution_102 {

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

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levelOrderTraversal = new ArrayList<>();
        if (root == null) return levelOrderTraversal;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int currLevel = 1;
        int nextLevel = 0;
        List<Integer> currentLevelTraversal = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            currentLevelTraversal.add(current.val);
            currLevel--;
            if (current.left != null) {
                queue.add(current.left);
                nextLevel++;
            }
            if (current.right != null) {
                queue.add(current.right);
                nextLevel++;
            }
            if (currLevel == 0) {
                currLevel = nextLevel;
                nextLevel = 0;
                levelOrderTraversal.add(currentLevelTraversal);
                currentLevelTraversal = new ArrayList<>();
            }
        }
        return levelOrderTraversal;
    }
}