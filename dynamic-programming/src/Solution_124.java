import java.util.HashMap;
import java.util.Map;

/**
 * https://www.youtube.com/watch?v=Osz-Vwer6rw&list=PL_z_8CaSLPWekqhdCPmFohncHwz8TY2Go&index=49
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/
 */
class Solution_124 {


    class Approach_1 {

        private Map<TreeNode, Integer> memo;

        int dfs(TreeNode root) {
            if (root == null) {
                return 0;
            }
            if (memo.containsKey(root)) {
                return memo.get(root);
            }
            TreeNode left = root.left;
            int leftSum = dfs(left);
            TreeNode right = root.right;
            int rightSum = dfs(right);
            int maxSum = Math.max(0, Math.max(leftSum, rightSum)) + root.val;
            memo.put(root, maxSum);
            return maxSum;
        }

        int max = Integer.MIN_VALUE;

        void maxFindDfs(TreeNode root) {
            if (root == null) {
                return;
            }
            int leftSum = Math.max(0, memo.getOrDefault(root.left, 0));
            int rightSum = Math.max(0, memo.getOrDefault(root.right, 0));
            max = Math.max(max, (leftSum + rightSum + root.val));
            maxFindDfs(root.left);
            maxFindDfs(root.right);
        }


        public int maxPathSum(TreeNode root) {
            memo = new HashMap<>();
            dfs(root);
            maxFindDfs(root);
            return max;
        }
    }

    class Approach_2 {

        int sum;

        /**
         * Max path sum is defined as the sum of nodes values on any path on the tree ( where path is defined between any source and destination node ).
         * It is not necessary for source and destination to be leaf node like diameter
         * we calculate maxPathSumInSubTreeFromRootNode() for left and right child,
         * Using this we could calculate the max sum path for each node , assuming path pass through root. Take maximum of those path to get the answer
         */
        private int maxPathSumInSubTreeFromRootNode(TreeNode root) {
            if (root == null) return 0;
            int ls = maxPathSumInSubTreeFromRootNode(root.left);
            int rs = maxPathSumInSubTreeFromRootNode(root.right);
            sum = Math.max(sum, ls + rs + root.val);
            int maxPathSumInSubtreeFromRoot = Math.max(ls, rs) + root.val;
            return Math.max(0, maxPathSumInSubtreeFromRoot); // because source and destination not necessary to leaf we could reject the sum if it is not adding value.
        }

        public int maxPathSum(TreeNode root) {
            if (root == null) return 0;
            sum = Integer.MIN_VALUE;
            maxPathSumInSubTreeFromRootNode(root);
            return sum;
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