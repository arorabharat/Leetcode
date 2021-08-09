import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
 */
class Solution_103 {

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);
        Deque<Integer> currLevel = new LinkedList<>();
        boolean isRightDir = true;
        while (!queue.isEmpty()) {
            TreeNode front = queue.poll();
            if (front == null && queue.isEmpty()) break;
            if (front == null) {
                result.add(new ArrayList<>(currLevel));
                currLevel = new LinkedList<>();
                queue.add(null); // end of the level marker
                isRightDir = !isRightDir; // flip the direction
            } else {
                if (isRightDir) currLevel.addLast(front.val); // left to right
                else currLevel.addFirst(front.val); // right to left
                if (front.left != null) {
                    queue.add(front.left);
                }
                if (front.right != null) {
                    queue.add(front.right);
                }
            }
        }
        result.add(new ArrayList<>(currLevel));
        return result;
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