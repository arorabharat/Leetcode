import java.util.ArrayList;
import java.util.List;

// todo not able to do as of now
public class Solution_95 {
    class Approach_1 {

        private List<TreeNode> _generateTrees(int s, int e) {
            List<TreeNode> list = new ArrayList<>();
            if (s > e) {
                return list;
            }
            if (s == e) {
                TreeNode treeNode = new TreeNode(s);
                list.add(treeNode);
                return list;
            }
            for (int i = s; i <= e; i++) {
                List<TreeNode> leftCombinations = _generateTrees(s, i - 1);
                List<TreeNode> rightCombinations = _generateTrees(i + i, e);
                for (TreeNode left : leftCombinations) {
                    for (TreeNode right : rightCombinations) {
                        TreeNode root = new TreeNode(i);
                        root.left = left;
                        root.right = right;
                        list.add(root);
                    }
                }
                if (leftCombinations.isEmpty()) {
                    for (TreeNode right : rightCombinations) {
                        TreeNode root = new TreeNode(i);
                        root.right = right;
                        list.add(root);
                    }
                }
                if (rightCombinations.isEmpty()) {
                    for (TreeNode left : leftCombinations) {
                        TreeNode root = new TreeNode(i);
                        root.left = left;
                        list.add(root);
                    }
                }
            }
            return list;
        }

        public List<TreeNode> generateTrees(int n) {
            return _generateTrees(1, n);
        }
    }

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
}
