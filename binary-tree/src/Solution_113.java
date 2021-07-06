import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/path-sum-ii/
 */
class Solution_113 {

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
        for (Deque<Integer> path : pathLeftList) {
            path.addFirst(root.val);
            pathRoot.add(path);
        }
        for (Deque<Integer> path : pathRightList) {
            path.addFirst(root.val);
            pathRoot.add(path);
        }
        return pathRoot;
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<Deque<Integer>> pathDeque = _pathSum(root, targetSum);
        List<List<Integer>> pathList = new ArrayList<>();
        for (Deque<Integer> path : pathDeque) {
            pathList.add(new ArrayList<>(path));
        }
        return pathList;
    }
}