import java.util.ArrayList;
import java.util.List;

public class Solution_99 {

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

    class Solution {


        private List<TreeNode> inorderTraversal;

        void inOrder(TreeNode root) {
            if (root == null) {
                return;
            }
            inorderTraversal.add(root);
            inOrder(root.left);
            inOrder(root.right);
        }

        public void recoverTree(TreeNode root) {
            inorderTraversal = new ArrayList<>();
            int[] sortedArray = inorderTraversal.stream().mapToInt(r -> r.val).sorted().toArray();
            List<TreeNode> swapList = new ArrayList<>();
            for (int i = 0; i < sortedArray.length; i++) {
                if (sortedArray[i] != inorderTraversal.get(i).val) {
                    swapList.add(inorderTraversal.get(i));
                }
            }
            if (swapList.size() != 2) {
                throw new RuntimeException("More than two nodes need swapping");
            }
            TreeNode n1 = swapList.get(0);
            TreeNode n2 = swapList.get(1);
            swapNodes(n1, n2);
        }

        private void swapNodes(TreeNode n1, TreeNode n2) {
            TreeNode n1left = n1.left;
            TreeNode n1right = n1.right;
            TreeNode n2left = n2.left;
            TreeNode n2right = n2.right;
            n2.left = n1left;
            n2.right = n1right;
            n1.left = n2left;
            n1.right = n2right;
        }
    }
}
