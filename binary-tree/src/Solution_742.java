import java.util.*;

public class Solution_742 {

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

    class Solution1 {

        private void dfs(TreeNode root, Map<TreeNode, TreeNode> node2parent, TreeNode parent) {
            if (root == null) {
                return;
            }
            node2parent.put(root, parent);
            dfs(root.left, node2parent, root);
            dfs(root.right, node2parent, root);
        }

        private TreeNode find(TreeNode root, int k) {
            if (root == null) {
                return null;
            }
            if (root.val == k) {
                return root;
            }
            TreeNode leftResponse = find(root.left, k);
            if (leftResponse != null) {
                return leftResponse;
            }
            return find(root.right, k);
        }

        private TreeNode bfs(TreeNode targetNode, Map<TreeNode, TreeNode> node2parent) {
            Set<Integer> visited = new HashSet<>();
            Queue<TreeNode> q = new LinkedList<>();
            addToQueue(targetNode, visited, q);
            while (!q.isEmpty()) {
                TreeNode front = q.poll();
                if (front.left == null && front.right == null) {
                    return front;
                }
                addToQueue(front.left, visited, q);
                addToQueue(front.right, visited, q);
                addToQueue(node2parent.get(front), visited, q);
            }
            return null;
        }

        private static void addToQueue(TreeNode node, Set<Integer> visited, Queue<TreeNode> q) {
            if (node != null && !visited.contains(node.val)) {
                q.add(node);
                visited.add(node.val);
            }
        }

        public int findClosestLeaf(TreeNode root, int k) {
            Map<TreeNode, TreeNode> node2parent = new HashMap<>();
            dfs(root, node2parent, null);
            TreeNode targetNode = find(root, k);
            TreeNode leafNode = bfs(targetNode, node2parent);
            return (leafNode != null) ? leafNode.val : Integer.MAX_VALUE;
        }
    }


    class Solution2 {

        // Class variable to capture the target node during the DFS pass
        private TreeNode targetNode = null;

        private void dfs(TreeNode root, Map<TreeNode, TreeNode> node2parent, TreeNode parent, int k) {
            if (root == null) {
                return;
            }

            // Capture the target node when we find it
            if (root.val == k) {
                targetNode = root;
            }

            node2parent.put(root, parent);
            dfs(root.left, node2parent, root, k);
            dfs(root.right, node2parent, root, k);
        }

        private TreeNode bfs(TreeNode targetNode, Map<TreeNode, TreeNode> node2parent) {
            // Changed to Set<TreeNode> to store references directly
            Set<TreeNode> visited = new HashSet<>();
            Queue<TreeNode> q = new LinkedList<>();

            addToQueue(targetNode, visited, q);

            while (!q.isEmpty()) {
                TreeNode front = q.poll();

                // Check if it's a leaf node
                if (front.left == null && front.right == null) {
                    return front;
                }

                addToQueue(front.left, visited, q);
                addToQueue(front.right, visited, q);
                addToQueue(node2parent.get(front), visited, q);
            }
            return null;
        }

        private void addToQueue(TreeNode node, Set<TreeNode> visited, Queue<TreeNode> q) {
            if (node != null && !visited.contains(node)) {
                q.add(node);
                visited.add(node);
            }
        }

        public int findClosestLeaf(TreeNode root, int k) {
            Map<TreeNode, TreeNode> node2parent = new HashMap<>();

            // One pass to map parents and find the target node
            dfs(root, node2parent, null, k);

            TreeNode leafNode = bfs(targetNode, node2parent);
            return (leafNode != null) ? leafNode.val : Integer.MAX_VALUE;
        }
    }
}
