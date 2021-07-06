/**
 * https://leetcode.com/problems/flip-equivalent-binary-trees/
 */
class Solution_951 {

    private void flipChild(TreeNode root) {
        if (root == null) return;
        TreeNode originalLeft = root.left;
        root.left = root.right;
        root.right = originalLeft;
    }

    private void triangleMatch(TreeNode root1, TreeNode root2) {

    }

    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null) return false;
        System.out.println(root1.val + " " + root2.val);
        if (root1.val == root2.val) {

            int left1 = (root1.left != null) ? root1.left.val : Integer.MIN_VALUE;
            int left2 = (root2.left != null) ? root2.left.val : Integer.MIN_VALUE;
            int right1 = (root1.right != null) ? root1.right.val : Integer.MAX_VALUE;
            int right2 = (root2.right != null) ? root2.right.val : Integer.MAX_VALUE;

            if (left1 == right2 && right1 == left2) {
                flipChild(root2);
            }

            left1 = (root1.left != null) ? root1.left.val : Integer.MIN_VALUE;
            left2 = (root2.left != null) ? root2.left.val : Integer.MIN_VALUE;
            right1 = (root1.right != null) ? root1.right.val : Integer.MAX_VALUE;
            right2 = (root2.right != null) ? root2.right.val : Integer.MAX_VALUE;
            System.out.println(left1 + " " + right1 + " " + left2 + " " + right2);

            if (left1 == left2 && right1 == right2) {
                return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right);
            }
        }
        return false;
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