import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/binary-tree-paths/
 * TODO
 */
class Solution_257 {


    class Approach_1 {

        private String convertToString(Stack<Integer> stack) {
            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty()) {
                sb.append(stack.pop());
                if (!stack.isEmpty()) {
                    sb.append("->");
                }
            }
            return sb.toString();
        }

        public List<String> binaryTreePaths(TreeNode root) {
            List<Stack<Integer>> paths = binaryTreePathsHelper(root);
            List<String> strPaths = new ArrayList<>();
            for (Stack<Integer> path : paths) {
                strPaths.add(convertToString(path));
            }
            return strPaths;
        }

        public List<Stack<Integer>> binaryTreePathsHelper(TreeNode root) {
            List<Stack<Integer>> rootPath = new ArrayList<>();
            if (root == null) {
                return rootPath;
            }
            if (root.left == null && root.right == null) {
                Stack<Integer> path = new Stack<>();
                path.push(root.val);
                rootPath.add(path);
                return rootPath;
            }
            List<Stack<Integer>> leftPaths = binaryTreePathsHelper(root.left);
            List<Stack<Integer>> rightPaths = binaryTreePathsHelper(root.right);
            leftPaths.forEach(x -> x.push(root.val));
            rightPaths.forEach(x -> x.push(root.val));
            rootPath.addAll(leftPaths);
            rootPath.addAll(rightPaths);
            return rootPath;
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

//    public List<Deque<Integer>> _pathSum(Solution_113.TreeNode root) {
//        if (root == null) {
//            return new LinkedList<>();
//        }
//        if (root.left == null && root.right == null) {
//            if (root.val == targetSum) {
//                List<Deque<Integer>> pathList = new LinkedList<>();
//                Deque<Integer> path = new LinkedList<>();
//                path.add(root.val);
//                pathList.add(path);
//                return pathList;
//            }
//            return new LinkedList<>();
//        }
//        List<Deque<Integer>> pathLeftList = _pathSum(root.left, targetSum - root.val);
//        List<Deque<Integer>> pathRightList = _pathSum(root.right, targetSum - root.val);
//        List<Deque<Integer>> pathRoot = new LinkedList<>();
//        for (Deque<Integer> path : pathLeftList) {
//            path.addFirst(root.val);
//            pathRoot.add(path);
//        }
//        for (Deque<Integer> path : pathRightList) {
//            path.addFirst(root.val);
//            pathRoot.add(path);
//        }
//        return pathRoot;
//    }
//
//    public List<String> binaryTreePaths(TreeNode root) {
//        List<Deque<Integer>> pathDeque = _pathSum(root);
//        List<List<Integer>> pathList = new ArrayList<>();
//        for (Deque<Integer> path : pathDeque) {
//            pathList.add(new ArrayList<>(path));
//        }
//        return pathList;
//    }
}