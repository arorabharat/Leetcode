/**
 * https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/
 * TODO
 */
class Solution_1530 {

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


    int count = 0;

    public int[] countLeafDistance(TreeNode root, int distance) {
        if (root == null) return new int[11];
        if (root.left == null && root.right == null) {
            int[] dis = new int[11];
            dis[0] = 1;
            return dis;
        }
        int[] leftLeaves = countLeafDistance(root.left, distance);
        int[] rightLeaves = countLeafDistance(root.right, distance);
        for (int i = 9; i >= 0; i--) {
            // add 1 to distance
            leftLeaves[i + 1] = leftLeaves[i];
        }
        leftLeaves[0] = 0;
        for (int i = 9; i >= 0; i--) {
            // add 1 to distance
            rightLeaves[i + 1] = rightLeaves[i];
        }
        rightLeaves[0] = 0;
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if (i + j <= distance) {
                    count = count + leftLeaves[i] * rightLeaves[j];
                }
            }
        }
        for (int i = 0; i <= 10; i++) {
            leftLeaves[i] = leftLeaves[i] + rightLeaves[i];
        }
        return leftLeaves;
    }

    public int countPairs(TreeNode root, int distance) {
        countLeafDistance(root, distance);
        return this.count;
    }
}