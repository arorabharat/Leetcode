import java.util.ArrayList;
import java.util.List;

public class Solution_653 {

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

    void toSortList(TreeNode root, List<Integer> list) {
        if (root != null) {
            toSortList(root.left, list);
            list.add(root.val);
            toSortList(root.right, list);
        }
    }

    /**
     * Sort to list and use the two pointer approach
     */
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> arr = new ArrayList<>();
        toSortList(root, arr);
        int n = arr.size();
        int i = 0;
        int j = n - 1;
        while (i < j) {
            int a = arr.get(i);
            int b = arr.get(j);
            if (a + b > k) {
                j--;
            } else if (a + b == k) {
                return true;
            } else {
                i++;
            }
        }
        return false;
    }
}