import java.util.Objects;

/**
 * https://leetcode.com/problems/balanced-binary-tree/
 *
 * @see Solution_104
 */
class Solution_98 {

    // this will fail because of the integer overflow.
    class Approach_1 {

        private boolean _isValidBST(TreeNode root, int start, int end) {
            if (root == null) return true;
            if (root.val < start || root.val > end) return false;
            return _isValidBST(root.left, start, root.val - 1)
                    && _isValidBST(root.right, root.val + 1, end);
        }

        public boolean isValidBST(TreeNode root) {
            return _isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    // the same as approach 1 but upper and lower bound are not subtracted by 1,
    // hence it will not fail because of integer overflow
    class Approach_2 {

        private boolean _isValidBST(TreeNode root,
                                    long lowerLimit,
                                    long upperLimit) {
            if (Objects.isNull(root)) return true;
            if (root.val >= upperLimit || root.val <= lowerLimit) return false;
            return _isValidBST(root.left, lowerLimit, root.val)
                    && _isValidBST(root.right, root.val, upperLimit);
        }

        public boolean isValidBST(TreeNode root) {
            if (Objects.isNull(root)) return true;
            return _isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }
    }

    // cast to long to prevent the integer overflow..
    class Approach_3 {

        private boolean inRange(long x, long min, long max) {
            return min <= x && x <= max;
        }

        private boolean _isValidBST(TreeNode root, long min, long max) {
            if (root == null) return true;
            return inRange(root.val, min, max)
                    && _isValidBST(root.left, min, (long) root.val - 1)
                    && _isValidBST(root.right, (long) root.val + 1, max);
        }

        public boolean isValidBST(TreeNode root) {
            return _isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }
    }

    // same as approach 2, just we do not pass negative infinity and positive infinity.
    class Approach_4 {

        public boolean _isValidBST(TreeNode root, Integer low, Integer high) {
            if (root == null) {
                return true;
            }

            if ((low != null && root.val <= low) || (high != null && root.val >= high)) {
                return false;
            }
            return _isValidBST(root.right, root.val, high) && _isValidBST(root.left, low, root.val);
        }

        public boolean isValidBST(TreeNode root) {
            return _isValidBST(root, null, null);
        }

    }

    // this one is actually a different approach
    class Approach_5 {

        private Integer prev;

        public boolean _isValidBST(TreeNode root) {
            prev = null;
            return inorder(root);
        }

        private boolean inorder(TreeNode root) {
            if (root == null) {
                return true;
            }
            if (!inorder(root.left)) {
                return false;
            }
            if (prev != null && root.val <= prev) {
                return false;
            }
            prev = root.val;
            return inorder(root.right);
        }

        public boolean isValidBST(TreeNode root) {
            return _isValidBST(root);
        }
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