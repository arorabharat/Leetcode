import java.util.ArrayList;
import java.util.List;

public class Solution_1305 {

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

        private List<Integer> inOrder(TreeNode root) {
            List<Integer> orderedList = new ArrayList<>();
            if (root == null) {
                return orderedList;
            }
            inOrder(root, orderedList);
            return orderedList;
        }

        private void inOrder(TreeNode root, List<Integer> orderedList) {
            if (root == null) {
                return;
            }
            inOrder(root.left, orderedList); // travel left subtree (smaller elements)
            orderedList.add(root.val);
            inOrder(root.right, orderedList); // travel right subtree (smaller elements)
        }

        private List<Integer> mergeSortedList(List<Integer> l1, List<Integer> l2) {
            if (l1 == null || l1.isEmpty()) {
                return l2;
            }
            if (l2 == null || l2.isEmpty()) {
                return l1;
            }
            int i = 0;
            int j = 0;
            int m = l1.size();
            int n = l2.size();
            List<Integer> result = new ArrayList<>();
            while (i < m && j < n) {
                if (l1.get(i) < l2.get(j)) {
                    result.add(l1.get(i));
                    i++;
                } else {
                    result.add(l2.get(j));
                    j++;
                }
            }
            while (i < m) {
                result.add(l1.get(i));
                i++;
            }
            while (j < n) {
                result.add(l2.get(j));
                j++;
            }
            return result;
        }

        public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
            List<Integer> orderedList1 = inOrder(root1);
            List<Integer> orderedList2 = inOrder(root2);
            return mergeSortedList(orderedList1, orderedList2);
        }
    }
}
