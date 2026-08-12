import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution_2096 {

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

    private boolean find(TreeNode root, int target, Stack<Character> path) {
        if (root == null) {
            return false;
        }
        if (root.val == target) {
            return true;
        }
        if (find(root.left, target, path)) {
            path.add('L');
            return true;
        }
        if (find(root.right, target, path)) {
            path.add('R');
            return true;
        }
        return false;
    }

    public String getDirections(TreeNode root, int startValue, int destValue) {
        Stack<Character> startPathBuilder = new Stack<>();
        Stack<Character> destPathBuilder = new Stack<>();
        StringBuilder startToDestPath = new StringBuilder();
        find(root, startValue, startPathBuilder);
        find(root, destValue, destPathBuilder);
        while (!startPathBuilder.isEmpty() && !destPathBuilder.isEmpty() && startPathBuilder.peek() == destPathBuilder.peek()) {
            startPathBuilder.pop();
            destPathBuilder.pop();
        }
        while (!startPathBuilder.isEmpty()) {
            startToDestPath.append("U");
            startPathBuilder.pop();
        }
        while (!destPathBuilder.isEmpty()) {
            startToDestPath.append(destPathBuilder.pop());
        }
        return startToDestPath.toString();
    }
}
