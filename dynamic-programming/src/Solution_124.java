/**
 * https://www.youtube.com/watch?v=Osz-Vwer6rw&list=PL_z_8CaSLPWekqhdCPmFohncHwz8TY2Go&index=49
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/
 */
class Solution_124 {


    class Approach_1 {
        /**
         * Single-pass DFS approach (optimized from original two-pass with memoization).
         * 
         * Algorithm:
         * For each node, we compute two things:
         * 1. Max path sum going THROUGH this node (can use both left and right branches)
         * 2. Max path sum going DOWNWARD from this node (for parent's calculation)
         * 
         * Key insight: When computing path through a node, only include non-negative
         * contributions from children to maximize the sum.
         */
        private int maxSum = Integer.MIN_VALUE;

        /**
         * Returns maximum path sum starting from root going downward (to any descendant).
         * Updates global maxSum when considering paths through each node.
         */
        private int maxPathSumDownward(TreeNode root) {
            if (root == null) {
                return 0;
            }

            int leftSum = maxPathSumDownward(root.left);
            int rightSum = maxPathSumDownward(root.right);

            // Maximum path sum going through current node
            // Only consider non-negative contributions from left and right
            int pathThroughNode = Math.max(0, leftSum) + Math.max(0, rightSum) + root.val;
            maxSum = Math.max(maxSum, pathThroughNode);

            // Return maximum path sum downward from current node
            // Choose the better branch (left or right) and add current node's value
            int maxPathDownward = Math.max(0, Math.max(leftSum, rightSum)) + root.val;
            return maxPathDownward;
        }

        public int maxPathSum(TreeNode root) {
            maxSum = Integer.MIN_VALUE;
            maxPathSumDownward(root);
            return maxSum;
        }
    }

    class Approach_2 {
        /**
         * Same algorithm as Approach_1, but with more concise variable naming.
         * 
         * Max path sum is defined as the sum of node values on any path in the tree
         * (where path is defined between any source and destination node).
         * It is not necessary for source and destination to be leaf nodes (unlike diameter problem).
         * 
         * Time: O(n) - single pass through all nodes
         * Space: O(h) - recursion stack, where h is height of tree
         */
        private int maxSum = Integer.MIN_VALUE;

        /**
         * Returns maximum path sum starting from root going downward to any descendant.
         * Updates global maxSum when considering paths that go through each node.
         */
        private int maxPathSumDownward(TreeNode root) {
            if (root == null) {
                return 0;
            }

            // Get max path sum downward from left and right children
            int leftSum = maxPathSumDownward(root.left);
            int rightSum = maxPathSumDownward(root.right);

            // Path going through current node (can use both left and right branches)
            // Only include non-negative contributions to maximize the sum
            int pathThroughNode = Math.max(0, leftSum) + Math.max(0, rightSum) + root.val;
            maxSum = Math.max(maxSum, pathThroughNode);

            // Return max path sum downward from current node (for parent)
            // Can only choose one branch (left or right) when going upward
            int maxPathDownward = Math.max(0, Math.max(leftSum, rightSum)) + root.val;
            return maxPathDownward;
        }

        public int maxPathSum(TreeNode root) {
            maxSum = Integer.MIN_VALUE;
            maxPathSumDownward(root);
            return maxSum;
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