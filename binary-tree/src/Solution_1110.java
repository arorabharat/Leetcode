import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/delete-nodes-and-return-forest/
 */
class Solution_1110 {

    /**
     * we will do bfs traversal,
     * (Strategy 1)  every time we encounter a node which need to be deleted we will first add its child to the queue and make it's left and right pointer null
     * (Strategy 2)  every time we encounter a node whose child need to be deleted , we will first add its child to the queue then we will remove the link between parent and child ie parent.left == null or parent.right == null
     * There are four cases
     * Parent(delete) - child(keep) - edge need between parent and child need to be deleted (Strategy 1)
     * Parent(delete) - child(delete) - edge between parent and child need to be deleted (Strategy 1)
     * Parent(keep) - child(delete) : edge between parent and child need to deleted (Strategy 22)
     * Parent(keep) - child(keep) : nothing need to be done, all there edges will be same in the final tree
     * Combination of both the strategy solve our problem in linear time.
     * If node does not have any parent then it is not connected with rest of tree so we need to add it in the list
     */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        int m = 1001;
        List<TreeNode> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        if (to_delete.length == 0) {
            ans.add(root);
            return ans;
        }
        boolean[] noParent = new boolean[m];
        boolean[] delete = new boolean[m];
        for (int j : to_delete) {
            delete[j] = true;
        }
        noParent[root.val] = true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode front = queue.remove();
            TreeNode left = front.left;
            TreeNode right = front.right;

            if (delete[front.val]) {
                if (left != null) noParent[left.val] = true;
                if (right != null) noParent[right.val] = true;
            } else if (noParent[front.val]) {
                ans.add(front);
            }

            if (left != null) {
                queue.add(left);
                if (delete[left.val]) front.left = null;
            }
            if (right != null) {
                queue.add(right);
                if (delete[right.val]) front.right = null;
            }
        }
        return ans;
    }

    /**
     * Similar to bfs
     */
    private void preOrder(TreeNode root, boolean noParent, boolean[] delete, List<TreeNode> ans) {
        if (root == null) return;
        TreeNode left = root.left;
        TreeNode right = root.right;
        boolean noParentNext = false;
        // strategy 1
        if (delete[root.val]) {
            root.left = null;
            root.right = null;
            noParentNext = true;
        } else if (noParent) {
            ans.add(root);
        }
        // strategy 2
        if (left != null && delete[left.val]) {
            root.left = null;
        }
        if (right != null && delete[right.val]) {
            root.right = null;
        }
        preOrder(left, noParentNext, delete, ans);
        preOrder(right, noParentNext, delete, ans);
    }

    public List<TreeNode> delNodes2(TreeNode root, int[] to_delete) {
        int m = 1001;
        List<TreeNode> ans = new ArrayList<>();
        if (to_delete.length == 0) {
            ans.add(root);
            return ans;
        }
        boolean[] delete = new boolean[m];
        for (int j : to_delete) {
            delete[j] = true;
        }
        preOrder(root, true, delete, ans);
        return ans;
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