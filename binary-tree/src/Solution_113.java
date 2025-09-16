import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/path-sum-ii/
 * TODO read solution
 *
 * @see Solution_257
 * @see Solution_437
 * @see Solution_666
 */
class Solution_113 {


    class Approach_1 {

        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {

        }
    }

    public List<Deque<Integer>> _pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return new LinkedList<>();
        }
        if (root.left == null && root.right == null) {
            if (root.val == targetSum) {
                List<Deque<Integer>> pathList = new LinkedList<>();
                Deque<Integer> path = new LinkedList<>();
                path.add(root.val);
                pathList.add(path);
                return pathList;
            }
            return new LinkedList<>();
        }
        List<Deque<Integer>> pathLeftList = _pathSum(root.left, targetSum - root.val);
        List<Deque<Integer>> pathRightList = _pathSum(root.right, targetSum - root.val);
        List<Deque<Integer>> pathRoot = new LinkedList<>();
        pathLeftList.forEach(path -> {
            path.addFirst(root.val);
            pathRoot.add(path);
        });

        pathRightList.forEach(path -> {
            path.addFirst(root.val);
            pathRoot.add(path);
        });
        return pathRoot;
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<Deque<Integer>> pathDeque = _pathSum(root, targetSum);
        List<List<Integer>> pathList = new ArrayList<>();
        pathDeque.forEach(path -> {
            pathList.add(new ArrayList<>(path));
        });
        return pathList;
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