import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * https://leetcode.com/problems/balance-a-binary-search-tree/
 */
class Solution_1382 {

    class TreeNode {
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

    List<TreeNode> arr;

    void inOrder(TreeNode root) {
        if (root != null) {
            inOrder(root.left);
            arr.add(root);
            inOrder(root.right);
        }
    }

    List<TreeNode> sort(TreeNode root) {
        arr = new ArrayList<>();
        inOrder(root);
        for (TreeNode t : arr) {
            t.left = t.right = null;
        }
        return arr;
    }

    TreeNode createBst(List<TreeNode> list) {
        if (list.isEmpty()) return null;
        return _createBst(list, 0, list.size() - 1);
    }

    TreeNode _createBst(List<TreeNode> list, int start, int end) {

        // Item 49
        Objects.requireNonNull(list);

        if (start == end) {
            return list.get(start);
        }

        if (start < end) {
            int mid = (start + end) / 2;
            TreeNode root = list.get(mid);
            root.left = _createBst(list, start, mid - 1);
            root.right = _createBst(list, mid + 1, end);
            return root;
        }
        return null;
    }

    public TreeNode balanceBST(TreeNode root) {
        List<TreeNode> sortedTree = sort(root);
        return createBst(sortedTree);
    }
}
