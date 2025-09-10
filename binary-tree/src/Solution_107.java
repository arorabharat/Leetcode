import java.util.*;

/**
 * https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
 */
class Solution_107 {

    class Approach_1 {

        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            List<List<Integer>> levelTraversal = new ArrayList<>();
            if (root == null) return levelTraversal;
            Queue<TreeNode> q = new LinkedList<>();
            q.add(root);
            Stack<List<Integer>> forwardLevelTraversal = new Stack<>();
            while (!q.isEmpty()) {
                int size = q.size();
                List<Integer> currLevelTraversal = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    TreeNode node = q.poll();
                    currLevelTraversal.add(node.val);
                    if (node.left != null) q.add(node.left);
                    if (node.right != null) q.add(node.right);
                }
                forwardLevelTraversal.add(currLevelTraversal);
            }
            while (!forwardLevelTraversal.isEmpty()) {
                levelTraversal.add(forwardLevelTraversal.pop());
            }
            return levelTraversal;
        }
    }

    // same as 1, just add in reverse order in the list
    class Approach_2 {

        public List<List<Integer>> levelOrderBottom(TreeNode root) {
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
    }

    // requires counting of current and next level.
    class Approach_3 {

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

    // same as 1 but uses two list one for curr and one for next level
    class Approach_4 {

        public List<List<Integer>> levelOrderBottom(TreeNode root) {
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