import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/construct-string-from-binary-tree/
 */
public class Solution_606 {

    /**
     * You can not convert integer to character , you can convert digit to string
     * So always choose integer to string not character
     */
    private Deque<String> _tree2str(TreeNode root) {
        if (root == null) return new LinkedList<>();
        Deque<String> left = _tree2str(root.left);
        Deque<String> right = _tree2str(root.right);
        if (!left.isEmpty() || !right.isEmpty()) {
            left.addFirst("(");
            left.addLast(")");
        }
        if (!right.isEmpty()) {
            right.addFirst("(");
            right.addLast(")");
            left.addAll(right);
        }
        left.addFirst(Integer.toString(root.val));
        return left;
    }

    public String tree2str(TreeNode root) {
        if (root == null) return "";
        Deque<String> deque = _tree2str(root);
        StringBuilder sb = new StringBuilder();
        for (String str : deque) {
            sb.append(str);
        }
        return sb.toString();
    }

    private String _tree2str1(TreeNode root) {
        if (root == null) return "";
        String left = _tree2str1(root.left);
        String right = _tree2str1(root.right);
        if (!left.isEmpty() || !right.isEmpty()) {
            left = "(" + left;
            left = left + ")";
        }
        if (!right.isEmpty()) {
            right = "(" + right;
            right = right + ")";
        }
        return root.val + left + right;
    }

    public String tree2str1(TreeNode root) {
        return _tree2str1(root);
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

