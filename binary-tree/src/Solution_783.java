import java.util.Objects;

/**
 * https://leetcode.com/problems/minimum-distance-between-bst-nodes/
 *
 * @see Solution_366
 * @see Solution_549
 * @see Solution_1485
 */
class Solution_783 {

    // TODO: 20/04/25 - this solution is not working need to fix it
    public Pair<Integer> _minDiffInBST4(TreeNode root) {
        if (Objects.isNull(root)) {
            return new Pair<>(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        if (Objects.isNull(root.left) && Objects.isNull(root.right)) {
            return new Pair<>(root.val, root.val, Integer.MAX_VALUE);
        }
        int value = root.val;
        Pair<Integer> left = _minDiffInBST4(root.left);
        Pair<Integer> right = _minDiffInBST4(root.right);
        int minVal = Math.min(left.minDiff, right.minDiff);
        minVal = Math.min(minVal, (value - left.max));
        return new Pair<>(left.min, right.max, minVal);
    }

    public int minDiffInBST4(TreeNode root) {
        return _minDiffInBST4(root).minDiff;
    }

    static class Pair<T> {

        private final T min;
        private final T max;
        private final Integer minDiff;

        public Pair(T min, T max, Integer minDiff) {
            this.min = min;
            this.max = max;
            this.minDiff = minDiff;
        }

        public Integer getMinDiff() {
            return minDiff;
        }

        public T getMin() {
            return min;
        }

        public T getMax() {
            return max;
        }
    }

    int minDifference = Integer.MAX_VALUE;
    Integer prev, ans;

    private TreeNode inOrder(TreeNode root, TreeNode lastTraverseNode) {
        if (root != null) {
            if (root.left != null) {
                TreeNode leftNode = inOrder(root.left, lastTraverseNode);
                lastTraverseNode = leftNode != null ? leftNode : lastTraverseNode;
            }
            if (lastTraverseNode != null) {
                int absDiff = root.val - lastTraverseNode.val;
                minDifference = Math.min(minDifference, absDiff);
            }
            lastTraverseNode = root;
            if (root.right != null) {
                TreeNode rightNode = inOrder(root.right, lastTraverseNode);
                lastTraverseNode = rightNode != null ? rightNode : lastTraverseNode;
            }
        }
        return lastTraverseNode;
    }

    public int minDiffInBST(TreeNode root) {
        minDifference = Integer.MAX_VALUE;
        inOrder(root, null);
        return minDifference;
    }

    public int minDiffInBST1(TreeNode root) {
        prev = null;
        ans = Integer.MAX_VALUE;
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode node) {
        if (node == null) return;
        dfs(node.left);
        if (prev != null)
            ans = Math.min(ans, node.val - prev);
        prev = node.val;
        dfs(node.right);
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