/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * Lowest common ancestor special case.
 * Where keys exits
 * Only two nodes
 * Keys are unique
 *
 * @see Solution_235
 */
class Solution_236 {

    class Approach_1 {

        public SearchResult _lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null) {
                return new SearchResult(false, false, null);
            }
            SearchResult leftResults = _lowestCommonAncestor(root.left, p, q);
            if (leftResults.lca != null) {
                return leftResults;
            }
            SearchResult nodeResults = new SearchResult(p == root, q == root, null);
            leftResults.merge(root, nodeResults);
            if (leftResults.lca != null) {
                return leftResults;
            }
            SearchResult rightResults = _lowestCommonAncestor(root.right, p, q);
            if (rightResults.lca != null) {
                return rightResults;
            }
            rightResults.merge(root, leftResults);
            return rightResults;
        }

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || p == null || q == null) return null;
            return _lowestCommonAncestor(root, p, q).lca;
        }

        class SearchResult {
            boolean p;
            boolean q;
            TreeNode lca;

            public SearchResult(boolean p, boolean q, TreeNode lca) {
                this.p = p;
                this.q = q;
                this.lca = lca;
            }

            private void merge(TreeNode root, final SearchResult nodeResults) {
                this.p = this.p || nodeResults.p;
                this.q = this.q || nodeResults.q;
                if (this.p && this.q) {
                    this.lca = root;
                }
            }
        }
    }


    class Approach_2 {

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || p == null || q == null) return null;
            if (p == q) return p; // optional: define LCA(p, p) = p
            return dfs(root, p, q).lca;
        }

        private SearchResult dfs(TreeNode node, TreeNode p, TreeNode q) {
            if (node == null) return new SearchResult(0, null);

            SearchResult left = dfs(node.left, p, q);
            if (left.lca != null) return left;

            SearchResult right = dfs(node.right, p, q);
            if (right.lca != null) return right;

            int here = (node == p || node == q) ? 1 : 0;
            int found = left.found + right.found + here;

            TreeNode lca = (found == 2) ? node : null;
            return new SearchResult(found, lca);
        }

        static final class SearchResult {
            final int found;     // how many of {p, q} were found in this subtree (0..2)
            final TreeNode lca;  // non-null only when both are found

            SearchResult(int found, TreeNode lca) {
                this.found = found;
                this.lca = lca;
            }
        }
    }

    // this is less efficient but generic solution
    class Approach_3 {

        private SearchResult _lowestCommonAncestor(TreeNode root, TreeNode... nodes) {
            int length = nodes.length;
            if (root == null) return new SearchResult(length);
            TreeNode left = root.left;
            TreeNode right = root.right;
            SearchResult leftResult = _lowestCommonAncestor(left, nodes);
            if (leftResult.lca != null) return leftResult;
            SearchResult rightResult = _lowestCommonAncestor(right, nodes);
            if (rightResult.lca != null) return rightResult;
            boolean[] nodesExists = new boolean[length];
            int countOfMatched = 0;
            for (int i = 0; i < length; i++) {
                if (!nodesExists[i]) {
                    if (leftResult.nodesExists[i] || rightResult.nodesExists[i] || root.val == nodes[i].val) {
                        countOfMatched++;
                        nodesExists[i] = true;
                    }
                }
            }
            TreeNode ancestor = (countOfMatched == length) ? root : null;
            return new SearchResult(ancestor, nodesExists);
        }

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            return _lowestCommonAncestor(root, p, q).lca;
        }

        static class SearchResult {
            TreeNode lca;
            boolean[] nodesExists;

            SearchResult(TreeNode lca, boolean[] nodesExists) {
                this.lca = lca;
                this.nodesExists = nodesExists;
            }

            SearchResult(int n) {
                this.lca = null;
                this.nodesExists = new boolean[n];
            }
        }
    }

    /**
     * This is approach seems easy but it has multiple flaws,
     * 1. it is not consistent in terms of method convention, lets say on root node
     * we are looking for common ancestor of 2 and 5 in the following tree and when we call method on the left subtree of root it return 5.
     * Which is not ancestor of the 2 and 5
     * 2
     * /
     * 5
     * 2. It does not work if the node does node exit in the tree.
     * we are looking for common ancestor of 2 and 5 in the following tree and when we call method on the left subtree of root it return 2.
     * Which is not ancestor of the 2 and 5, and further root will return 1 which is wrong.
     * 1
     * /
     * 2
     * /
     * 6
     */
    class Approach_4 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null || root == p || root == q) return root;

            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);

            if (left != null && right != null) return root; // p and q found in different branches
            return (left != null) ? left : right;           // bubble up the non-null side (or null)
        }
    }


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}