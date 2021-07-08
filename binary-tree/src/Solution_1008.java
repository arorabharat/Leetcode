/**
 * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
 * <p>
 * TODO : implement state less method
 *
 * @see Solution_109
 * @see Solution_1007
 * @see Solution_1654
 */
class Solution_1008 {

    private int i;

    TreeNode deserialize(int[] preorder, int start, int end) {
        if (i == preorder.length) return null;
        int val = preorder[i];
        if (start < val && val < end) {
            TreeNode root = new TreeNode(val);
            i++;
            root.left = deserialize(preorder, start, root.val);
            root.right = deserialize(preorder, root.val, end);
            return root;
        }
        return null;
    }

    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder.length == 0) {
            return null;
        }
        i = 0;
        return deserialize(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
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