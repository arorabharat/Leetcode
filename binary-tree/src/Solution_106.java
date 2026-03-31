public class Solution_106 {

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

    class Solution1 {

        private TreeNode _buildTree(int[] inorder, int[] postorder, int is, int ps, int pe) {
            if (ps == pe) {
                return new TreeNode(pe);
            }
            TreeNode root = new TreeNode(postorder[pe]);
            int rootIndex = is;
            while (inorder[rootIndex] != root.val) {
                rootIndex++;
            }
            root.left = _buildTree(inorder, postorder, is, ps, rootIndex - 1);
            root.right = _buildTree(inorder, postorder, rootIndex + 1, rootIndex, pe - 1);
            return root;
        }

        public TreeNode buildTree(int[] inorder, int[] postorder) {
            return _buildTree(inorder, postorder, 0, 0, inorder.length - 1);
        }
    }
}
