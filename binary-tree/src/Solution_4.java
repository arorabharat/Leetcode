import java.util.ArrayList;
import java.util.List;

public class Solution_4 {

    public static class TreeNode {
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

        public boolean isSameTree(TreeNode r1, TreeNode r2) {
            if (r1 == null && r2 == null) return true;
            if (r1 == null || r2 == null) return false;
            if (r1.val != r2.val) return false;
            return isSameTree(r1.left, r2.left) && isSameTree(r1.right, r2.right);
        }

        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            if (root == null || subRoot == null) return false;
            return isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
        }
    }


    // this approach will not work, because you can not rely on the inorder and preOrder traversal to get the
    // same structure
    class Approach_2 {
        private void _preOrder(TreeNode root, List<Integer> orderedList) {
            if (root == null) {
                return;
            }
            orderedList.add(root.val);
            _preOrder(root.left, orderedList);
            _preOrder(root.right, orderedList);
        }

        private void _inOrder(TreeNode root, List<Integer> orderedList) {
            if (root == null) {
                return;
            }
            _inOrder(root.left, orderedList);
            orderedList.add(root.val);
            _inOrder(root.right, orderedList);
        }

        private List<Integer> preOrder(TreeNode root) {
            List<Integer> orderedList = new ArrayList<>();
            _preOrder(root, orderedList);
            return orderedList;
        }

        private List<Integer> inOrder(TreeNode root) {
            List<Integer> orderedList = new ArrayList<>();
            _inOrder(root, orderedList);
            return orderedList;
        }

        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            if (root == null || subRoot == null) return false;
            List<Integer> rootPreOrder = preOrder(root);
            List<Integer> rootInOrder = inOrder(root);
            List<Integer> subRootPreOrder = preOrder(subRoot);
            List<Integer> subRootInOrder = inOrder(subRoot);
            String rootPreOrderStr = convertToString(rootPreOrder);
            String rootInOrderStr = convertToString(rootInOrder);
            String subRootPreOrderStr = convertToString(subRootPreOrder);
            String subRootInOrderStr = convertToString(subRootInOrder);
            return rootPreOrderStr.contains(subRootPreOrderStr) && rootInOrderStr.contains(subRootInOrderStr);
        }

        private String convertToString(List<Integer> rootPreOrder) {
            StringBuilder rootPreOrderBuilder = new StringBuilder();
            rootPreOrder.forEach(rootPreOrderBuilder::append);
            return rootPreOrderBuilder.toString();
        }
    }
}
