import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
 * TODO : read solution
 */
class Solution_107 {

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

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int currentLevel = 1;
        int nextLevel = 0;
        int level = 0;
        while (!queue.isEmpty()) {
            TreeNode front = queue.remove();
            if (result.size() == level) {
                result.add(new ArrayList<>());
            }
            result.get(level).add(front.val);
            currentLevel--;
            if (Objects.nonNull(front.left)) {
                queue.add(front.left);
                nextLevel++;
            }
            if (Objects.nonNull(front.right)) {
                queue.add(front.right);
                nextLevel++;
            }
            if (currentLevel == 0) {
                currentLevel = nextLevel;
                nextLevel = 0;
                level++;
            }
        }
        Collections.reverse(result);
        return result;
    }
}