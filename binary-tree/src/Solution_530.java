import java.util.Objects;
import java.util.Stack;

public class Solution_530 {

    public int getMinimumDifference(TreeNode root) {
        if (Objects.isNull(root)) return Integer.MAX_VALUE;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode it = root;
        TreeNode prev = null;
        Integer diff = Integer.MAX_VALUE;
        while (Objects.nonNull(it.left)) stack.push(it.left);

        while (!stack.isEmpty()) {
            it = stack.pop();
            System.out.println(it.val);
            while (Objects.nonNull(it.left)) stack.push(it.left);
            if (Objects.nonNull(it.right)) stack.push(it.right);
        }

    }

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
}
