import java.util.*;

/**
 * https://leetcode.com/problems/cousins-in-binary-tree/
 */
class Solution_993 {

    /**
     * [
     * 1,
     * 2, 3,
     * n, 4, n,5,
     * n,n,6,n,7]
     * 7
     */

    public boolean directChild(TreeNode root, int x, int y) {
        if (root != null) {
            int leftVal = (root.left == null) ? -1 : root.left.val;
            int rightVal = (root.right == null) ? -1 : root.right.val;
            if ((leftVal == x && rightVal == y) || (leftVal == y && rightVal == x)) {
                return true;
            }
            return directChild(root.left, x, y) || directChild(root.right, x, y);
        }
        return false;
    }

    private void heightCalc(TreeNode root, int[] height, int h) {
        if (root != null) {
            height[root.val] = h;
            heightCalc(root.left, height, h + 1);
            heightCalc(root.right, height, h + 1);
        }
    }

    public boolean isCousins(TreeNode root, int x, int y) {
        int N = 101;
        int[] height = new int[N];
        Arrays.fill(height, -1);
        heightCalc(root, height, 0);
        if (height[x] != height[y]) {
            return false;
        }
        return !directChild(root, x, y);
    }

    // bfs traversal of the tree
    public boolean isCousins2(TreeNode root, int x, int y) {

        if (root == null) return false;

        Queue<TreeNode> q = new LinkedList<>();
        Set<Integer> hs = new HashSet<>();

        q.add(root);

        while (!q.isEmpty()) {

            int len = q.size();

            for (int i = 0; i < len; i++) {

                TreeNode curr = q.poll();

                //All nodes traversed in this level are added to a HashSet (Values of nodes are unique!)
                assert curr != null;
                hs.add(curr.val);

                //If both nodes have the same parent
                if (curr.left != null && curr.right != null && curr.left.val == x && curr.right.val == y) return false;

                //If both nodes have the same parent
                if (curr.left != null && curr.right != null && curr.left.val == y && curr.right.val == x) return false;

                //If HashSet contains both x & y, they are in the same level without same parent. Hence they are cousins!
                if (hs.contains(x) && hs.contains(y)) return true;

                if (curr.left != null) {
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    q.add(curr.right);
                }

            }
            hs = new HashSet<>();
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
