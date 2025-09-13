import java.util.*;

/**
 * https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 */
class Solution_230 {

    // recursive approach
    class Approach_1 {

        private void bstInorderTraversal(TreeNode root, List<Integer> sortedList) {
            if (root == null) return;
            bstInorderTraversal(root.left, sortedList);
            sortedList.add(root.val);
            bstInorderTraversal(root.right, sortedList);
        }

        public int kthSmallest(TreeNode root, int k) {
            List<Integer> sortedList = new ArrayList<>();
            bstInorderTraversal(root, sortedList);
            return sortedList.get(k - 1);
        }
    }

    // optimize for early close,
    // but this cannot be used in the REST api kind of area, as there might be multiple thread
    // calling the same method on the same object, while processing of another REST request is in process.
    class Approach_2 {

        private int count = 0;
        private int k;

        private int bstInorderTraversal(TreeNode root) {
            if (root == null) return -1;
            int res = bstInorderTraversal(root.left);
            if (res != -1) {
                return res;
            }
            count++;
            if (count == this.k) {
                return root.val;
            }
            return bstInorderTraversal(root.right);
        }

        public int kthSmallest(TreeNode root, int k) {
            this.count = 0;
            this.k = k;
            return bstInorderTraversal(root);
        }
    }

    class Approach_3 {

        int nodeCount = 0;

        private Optional<TreeNode> inorder(TreeNode root, int k) {
            if (root != null) {
                Optional<TreeNode> result = inorder(root.left, k);
                if (result.isPresent()) return result;
                nodeCount++;
                if (nodeCount == k) {
                    return Optional.of(root);
                }
                return inorder(root.right, k);
            }
            return Optional.empty();
        }

        public int kthSmallest(TreeNode root, int k) {
            this.nodeCount = 0;
            Optional<TreeNode> result = inorder(root, k);
            return result.map(treeNode -> treeNode.val).orElse(Integer.MIN_VALUE);
        }
    }

    class Approach_4 {

        public Pair _kthSmallest(TreeNode root, int k) {
            if (Objects.isNull(root)) return new Pair(0, null);
            if (k == 1) new Pair(null, root.val);
            Pair leftResult = _kthSmallest(root.left, k);
            if (Objects.nonNull(leftResult.kElement)) return leftResult;
            if (leftResult.count + 1 == k) return new Pair(null, root.val);
            Pair rightResult = _kthSmallest(root.right, k - leftResult.count - 1);
            if (Objects.nonNull(rightResult.kElement)) return rightResult;
            return new Pair(leftResult.count + 1 + rightResult.count, null);
        }

        public int kthSmallest(TreeNode root, int k) {
            return _kthSmallest(root, k).kElement;
        }

        static class Pair {
            Integer count;
            Integer kElement;

            public Pair(Integer count, Integer kElement) {
                this.count = count;
                this.kElement = kElement;
            }
        }
    }

    class Approach_5 {

        public int kthSmallest(TreeNode root, int k) {
            if (Objects.isNull(root)) {
                return -1;
            }
            int count = 0;
            Stack<TreeNode> stack = new Stack<>();
            TreeNode in = root;
            while (!stack.isEmpty() || in != null) {
                addLeftBranch(in, stack);
                TreeNode out = stack.pop();
                count++;
                if (count == k) {
                    return out.val;
                }
                in = out.right;
            }
            return -1;
        }

        private void addLeftBranch(TreeNode curr, Stack<TreeNode> stack) {
            while (curr != null) {
                stack.add(curr);
                curr = curr.left;
            }
        }
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