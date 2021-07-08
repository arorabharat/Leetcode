import java.util.*;

/**
 * https://leetcode.com/problems/find-duplicate-subtrees/
 *
 * @see Solution_449
 * @see Solution_297
 */
class Solution_652 {


    /**
     * @see DSA#TREE_SERIALIZATION
     * <p>
     * Approach 2
     * Serialize the bst
     */

    Map<String, List<TreeNode>> serializationToNodesMap;
    Map<TreeNode, String> nodeToSerializationMap;

    private boolean treeMatch(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null) return false;
        if (root2 == null) return false;
        if (root1.val != root2.val) {
            return false;
        }
        return treeMatch(root1.left, root2.left) && treeMatch(root1.right, root2.right);
    }

    List<TreeNode> bfs(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        List<TreeNode> bfsTraversal = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode curr = queue.remove();
            bfsTraversal.add(curr);
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }
        return bfsTraversal;
    }

    /**
     * Brute force technique.
     * In this technique, we get list of all nodes in the tree using the bfs traversal
     * Then traverse to through all possible pairs and check if the sub tree match, if the tree match then we mark all the matching tee to done so we do not have to travel
     * those sub tree again.
     * We add one of the root node of the matching sub trees in the final list.
     */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<TreeNode> bfsTraversal = bfs(root);
        int size = bfsTraversal.size();
        boolean[] done = new boolean[size];
        List<TreeNode> duplicateTrees = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (done[i]) continue;
            TreeNode root1 = bfsTraversal.get(i);
            done[i] = true;
            int count = 1;
            for (int j = i + 1; j < size; j++) {
                if (!done[j]) {
                    TreeNode root2 = bfsTraversal.get(j);
                    if (treeMatch(root1, root2)) {
                        done[j] = true;
                        count++;
                    }
                }
            }
            if (count > 1) {
                duplicateTrees.add(root1);
            }
        }
        return duplicateTrees;
    }

    private String preOderStringSerializer(TreeNode root) {
        if (root == null) return "";
        if (nodeToSerializationMap.containsKey(root)) return nodeToSerializationMap.get(root);
        String left = preOderStringSerializer(root.left);
        String right = preOderStringSerializer(root.right);
        String result = Integer.toString(root.val);
        if (!right.isEmpty()) {
            result = result + "(" + left + ")" + "(" + right + ")";
        }
        if (!left.isEmpty()) {
            result = result + "(" + left + ")";
        }
        if (!serializationToNodesMap.containsKey(result)) {
            serializationToNodesMap.put(result, new ArrayList<>());
        }
        serializationToNodesMap.get(result).add(root);
        nodeToSerializationMap.put(root, result);
        return result;
    }

    public List<TreeNode> findDuplicateSubtrees1(TreeNode root) {
        if (root == null) return new ArrayList<>();
        serializationToNodesMap = new HashMap<>();
        nodeToSerializationMap = new HashMap<>();
        preOderStringSerializer(root);
        List<TreeNode> duplaicateNodes = new ArrayList<>();
        for (String serialization : serializationToNodesMap.keySet()) {
            List<TreeNode> matchingNodes = serializationToNodesMap.get(serialization);
            if (matchingNodes.size() > 1) {
                duplaicateNodes.add(matchingNodes.get(0));
            }
        }
        return duplaicateNodes;
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