import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/find-bottom-left-tree-value/submissions/
 */
class Solution_513 {

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

    static class Pair<F, S> {
        F value;
        S depth;

        Pair(F value, S depth) {
            this.value = value;
            this.depth = depth;
        }
    }

    /**
     * This approach return the depth and deepest node at each level
     */
    private Pair<Integer, Integer> _findBottomLeftValue(TreeNode root) {
        if (root == null) return null;
        Pair<Integer, Integer> deepNode = new Pair<>(root.val, 1);
        Pair<Integer, Integer> left = _findBottomLeftValue(root.left);
        Pair<Integer, Integer> right = _findBottomLeftValue(root.right);
        if (left != null) {
            deepNode.value = left.value;
            deepNode.depth = left.depth + 1;
        }
        if (right != null) {
            if (left == null || left.depth < right.depth) {
                deepNode.value = right.value;
                deepNode.depth = right.depth + 1;
            }
        }
        return deepNode;
    }

    public int findBottomLeftValue(TreeNode root) {
        Pair<Integer, Integer> pair = _findBottomLeftValue(root);
        if (pair == null) {
            return Integer.MIN_VALUE;
        } else {
            return pair.value;
        }
    }

    /**
     * This solution stores the nodes at each level and return the left node at max level
     */
    public int findBottomLeftValue1(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        dfs(root, levels, 0);
        return levels.get(levels.size() - 1).get(0);
    }


    public void dfs(TreeNode root, List<List<Integer>> levels, int level) {
        if (root == null) {
            return;
        }
        if (levels.size() == level) {
            levels.add(new ArrayList<>());
        }
        levels.get(level).add(root.val);
        dfs(root.left, levels, level + 1);
        dfs(root.right, levels, level + 1);
    }

    /**
     * further optimisation - we don't need store all the nodes at each level
     * we can just store the first node encounter at each level
     */
    public int findBottomLeftValue2(TreeNode root) {
        Pair<Integer, Integer> p = new Pair<>(-1, -1);
        bottomDFS(root, p, 0);
        return p.value;
    }

    public static void bottomDFS(TreeNode root, Pair<Integer, Integer> pair, int h) {
        if (root == null) return;
        if (h > pair.depth) {
            pair.value = root.val;
            pair.depth = h;
        }
        bottomDFS(root.left, pair, h + 1);
        bottomDFS(root.right, pair, h + 1);
    }
}