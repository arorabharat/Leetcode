import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
 * TODO : read solution
 */
class Solution_107 {

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

    public List<List<Integer>> levelOrderBottom1(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) return levels;
        List<TreeNode> nextLevel = new ArrayList<>();
        nextLevel.add(root);
        List<TreeNode> currLevel;
        while (!nextLevel.isEmpty()) {
            currLevel = nextLevel;
            nextLevel = new ArrayList<>();
            levels.add(new ArrayList<>());
            for (TreeNode node : currLevel) {
                levels.get(levels.size() - 1).add(node.val);
                if (node.left != null)
                    nextLevel.add(node.left);
                if (node.right != null)
                    nextLevel.add(node.right);
            }
        }
        Collections.reverse(levels);
        return levels;
    }

    public List<List<Integer>> levelOrderBottom2(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                assert node != null;
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.addFirst(level);
        }
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