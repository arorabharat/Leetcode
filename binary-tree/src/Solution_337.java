public class Solution_337 {

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

    class Approach_1 {


        record Pair(int level1, int level2) {
        }

        public Pair _rob(TreeNode root) {
            if (root == null) {
                return new Pair(0, 0);
            }
            Pair leftOutcome = _rob(root.left);
            Pair rightOutcome = _rob(root.right);
            int overallMaxSum = root.val + leftOutcome.level2() + rightOutcome.level2();
            overallMaxSum = Math.max(overallMaxSum, (leftOutcome.level1() + rightOutcome.level1()));
            Pair pair = new Pair(overallMaxSum, leftOutcome.level1() + rightOutcome.level1());
            // System.out.println(root.val + " "+pair);
            return pair;
        }

        public int rob(TreeNode root) {
            return _rob(root).level1;
        }
    }

    class Approach_2 {

        record Result(int best, int skip) {
        }

        private Result dfs(TreeNode root) {
            if (root == null) {
                return new Result(0, 0);
            }

            Result left = dfs(root.left);
            Result right = dfs(root.right);

            int rob = root.val + left.skip + right.skip;
            int skip = left.best + right.best;

            return new Result(Math.max(rob, skip), skip);
        }

        public int rob(TreeNode root) {
            return dfs(root).best;
        }
    }
}
