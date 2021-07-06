import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/average-of-levels-in-binary-tree/
 *
 * @see Solution_129
 * @see Solution_1530
 * @see Solution_1740
 */
class Solution_637 {

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> levelAverage = new ArrayList<>();
        if (root == null) return levelAverage;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            double currentLevelSum = 0;
            for (int i = 0; i < levelSize; i++) {
                TreeNode front = queue.remove();
                currentLevelSum += front.val;
                if (front.left != null) {
                    queue.add(front.left);
                }
                if (front.right != null) {
                    queue.add(front.right);
                }
            }
            levelAverage.add(currentLevelSum / levelSize);
        }
        return levelAverage;
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