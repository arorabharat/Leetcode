import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 *
 * @see Solution_110
 * @see Solution_111
 * @see Solution_559
 * @see Solution_1376
 */
class Solution_104 {

    // with recursion
    class Approach_1 {

        public int maxDepth(TreeNode root) {
            if (root == null) return 0;
            return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
        }

    }

    // without recursion
    class Approach_2 {

        public int maxDepth(TreeNode root) {
            if (root == null) return 0;
            int height = 0;
            Queue<TreeNode> q = new LinkedList<>();
            q.add(root);
            int currLevel = 1;
            int nextLevel = 0;
            while (!q.isEmpty()) {
                TreeNode currNode = q.poll();
                currLevel--;
                if (currNode.left != null) {
                    q.add(currNode.left);
                    nextLevel++;
                }
                if (currNode.right != null) {
                    q.add(currNode.right);
                    nextLevel++;
                }
                if (currLevel == 0) {
                    currLevel = nextLevel;
                    nextLevel = 0;
                    height++;
                }
            }
            return height;
        }
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