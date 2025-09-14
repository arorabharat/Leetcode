import java.util.Objects;

public class Solution_700 {

    // pre order traversal
    class Approach_1 {
        public TreeNode searchBST(TreeNode root, int val) {
            if (root == null) return null;
            if (root.val == val) return root;
            TreeNode leftSearch = searchBST(root.left, val);
            if (leftSearch != null) {
                return leftSearch;
            }
            return searchBST(root.right, val);
        }
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (Objects.isNull(root)) return null;
        if (root.val == val) return root;
        TreeNode leftSearch = searchBST(root.left, val);
        if (Objects.nonNull(leftSearch)) return leftSearch;
        return searchBST(root.right, val);
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
