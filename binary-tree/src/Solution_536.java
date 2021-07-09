import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/construct-binary-tree-from-string/
 */
class Solution_536 {

    private Deque<String> tokenize(String s) {
        Deque<String> tree = new LinkedList<>();
        char[] c = s.toCharArray();
        int n = c.length;
        int i = 0;
        while (i < c.length) {
            StringBuilder sb = new StringBuilder();
            // accumulate integer char using ( and ) as delimiter
            while (i < n && (c[i] != '(' && c[i] != ')')) {
                sb.append(c[i]);
                i++;
            }
            if (!sb.toString().isEmpty()) {
                tree.add(sb.toString());
            }
            // add the delimiter
            if (i < n && (c[i] == '(' || c[i] == ')')) {
                tree.add(Character.toString(c[i]));
                i++;
            }
        }
        return tree;
    }

    /**
     * @see DSA#TREE_DESERIALIZATION
     */
    public TreeNode str2tree(String s) {
        Deque<String> tree = tokenize(s);
        return buildTree(tree);
    }

    private TreeNode buildTree(Deque<String> tree) {
        if (tree.isEmpty()) return null;
        String curr = tree.removeFirst();
        int val = Integer.parseInt(curr);
        TreeNode root = new TreeNode(val);
        if (tree.isEmpty() || tree.getFirst().equals(")")) return root;
        tree.removeFirst();
        root.left = buildTree(tree);
        tree.removeFirst();
        if (!tree.isEmpty() && tree.getFirst().equals("(")) {
            tree.removeFirst();
            root.right = buildTree(tree);
            tree.removeFirst();
        }
        return root;
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

    // PENDING Iterative solution
}