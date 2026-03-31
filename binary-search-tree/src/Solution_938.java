public class Solution_938 {

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

    class Solution {

        private boolean inRange(TreeNode root, int low, int high) {
            return low <= root.val && root.val <= high;
        }

        private boolean isLower(TreeNode root, int low) {
            return root.val < low;
        }

        public int rangeSumBST(TreeNode root, int low, int high) {
            int sum = 0;
            if (root == null) {
                return sum;
            }
            if (inRange(root, low, high)) {
                sum = root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
            } else if (isLower(root, low)) {
                sum = rangeSumBST(root.right, low, high);
            } else {
                sum = rangeSumBST(root.left, low, high);
            }
            return sum;
        }
    }
}
