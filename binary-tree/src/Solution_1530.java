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

    /**
     * We will do the bottom up traversal ,
     * For each node we will return how many nodes are at a particular distance.
     * As the distance could be between 1 - 10 only, we just need to maintain the dis[] array which store the count of nodes for each distance = 1,2, 3,.. 10
     * at given node Z if on the left side we have x nodes at dis[x] and y nodes at dis[y] on right side. Then total distance between the two leaves would be x + y.
     * If this distance is less than given we could take all possible pairs dis[x]*dis[y].
     * In this way we could calculate the pair for each node
     */
    public int[] countLeafDistance(TreeNode root, int distance) {
        if (root == null) return new int[11];
        if (root.left == null && root.right == null) {
            int[] dis = new int[11];
            dis[0] = 1;
            return dis;
        }
        int[] leftLeaves = countLeafDistance(root.left, distance);
        int[] rightLeaves = countLeafDistance(root.right, distance);
        System.arraycopy(leftLeaves, 0, leftLeaves, 1, 10);
        leftLeaves[0] = 0;
        System.arraycopy(rightLeaves, 0, rightLeaves, 1, 10);
        rightLeaves[0] = 0;
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= distance - i; j++) {
                count = count + leftLeaves[i] * rightLeaves[j];
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