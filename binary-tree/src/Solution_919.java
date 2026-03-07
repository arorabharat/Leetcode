import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Solution_919 {

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

    class CBTInserter {

        private final TreeNode root;
        private final Queue<TreeNode> leaveNodes;

        public CBTInserter(TreeNode root) {
            this.root = root;
            leaveNodes = new LinkedList<>();
            bfs(root);
        }

        void bfs(TreeNode root) {
            Queue<TreeNode> q = new LinkedList<>();
            q.add(root);
            // System.out.println(root.val);
            while (!q.isEmpty()) {
                TreeNode front = q.remove();
                // System.out.println(front.val);
                if (front.left == null || front.right == null) {
                    leaveNodes.add(front);
                }
                if (front.left != null && front.right != null) {
                    q.add(front.left);
                    q.add(front.right);
                } else if (front.left != null) {
                    q.add(front.left);
                } else if (front.right != null) {
                    q.add(front.right);
                }
            }
        }

        public int insert(int val) {
            TreeNode firstLeaf = leaveNodes.peek();
            assert firstLeaf != null;
            TreeNode newNode = new TreeNode(val);
            if (firstLeaf.left != null) {
                firstLeaf.right = newNode;
                leaveNodes.remove();
            } else {
                firstLeaf.left = newNode;
            }
            leaveNodes.add(newNode);
            return firstLeaf.val;
        }

        public TreeNode get_root() {
            return this.root;
        }
    }

/**
 * Your CBTInserter object will be instantiated and called as such:
 * CBTInserter obj = new CBTInserter(root);
 * int param_1 = obj.insert(val);
 * TreeNode param_2 = obj.get_root();
 */
}
