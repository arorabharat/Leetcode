import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
 */
class Solution_103 {

    class Approach_1 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> zigZagTraversal = new ArrayList<>();
            if (root == null) return zigZagTraversal;
            Queue<TreeNode> q = new LinkedList<>();
            q.add(root);
            boolean flip = false;
            while (!q.isEmpty()) {
                int size = q.size();
                List<Integer> currLevelTraversal = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    TreeNode node = q.poll();
                    currLevelTraversal.add(node.val);
                    if (node.left != null) q.add(node.left);
                    if (node.right != null) q.add(node.right);
                }
                if (flip) {
                    Collections.reverse(currLevelTraversal);
                }
                zigZagTraversal.add(currLevelTraversal);
                flip = !flip;
            }
            return zigZagTraversal;
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