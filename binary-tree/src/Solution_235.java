/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 * 
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 * According to the definition of LCA on Wikipedia: "The lowest common ancestor is defined between two 
 * nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node 
 * to be a descendant of itself)."
 *
 * @see Solution_236
 */
class Solution_235 {

    /**
     * Approach 1: Recursive Solution
     * 
     * Time Complexity: O(h) where h is the height of the tree
     * Space Complexity: O(h) for the recursion stack
     * 
     * Algorithm:
     * There are four cases to consider:
     * 1. If root is null, return null
     * 2. If root equals p or q, return root (current node is the LCA)
     * 3. If both p and q are greater than root, LCA is in the right subtree
     * 4. If both p and q are less than root, LCA is in the left subtree
     * 5. Otherwise, p and q are on opposite sides of root, so root is the LCA
     * 
     * Note: Given that p and q are unique and exist in the BST.
     */
    class Approach_1 {
        /**
         * Finds the lowest common ancestor of two nodes in a BST using recursion.
         * 
         * @param root The root of the BST
         * @param p First node
         * @param q Second node
         * @return The lowest common ancestor of p and q, or null if not found
         */
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null) {
                return null;
            }
            
            // If root is one of the nodes, it's the LCA
            if (root.val == p.val || root.val == q.val) {
                return root;
            }
            
            // Both nodes are in the right subtree
            if (root.val < p.val && root.val < q.val) {
                return lowestCommonAncestor(root.right, p, q);
            }
            
            // Both nodes are in the left subtree
            if (p.val < root.val && q.val < root.val) {
                return lowestCommonAncestor(root.left, p, q);
            }
            
            // Nodes are on opposite sides, root is the LCA
            return root;
        }
    }

    /**
     * Approach 2: Iterative Solution
     * 
     * Time Complexity: O(h) where h is the height of the tree
     * Space Complexity: O(1) - no recursion stack needed
     * 
     * This approach uses iteration instead of recursion, which avoids stack overflow
     * for very deep trees and uses constant space.
     */
    class Approach_2 {
        /**
         * Finds the lowest common ancestor of two nodes in a BST using iteration.
         * 
         * @param root The root of the BST
         * @param p First node
         * @param q Second node
         * @return The lowest common ancestor of p and q, or null if not found
         */
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            TreeNode current = root;
            
            while (current != null) {
                // Both nodes are in the right subtree
                if (current.val < p.val && current.val < q.val) {
                    current = current.right;
                }
                // Both nodes are in the left subtree
                else if (p.val < current.val && q.val < current.val) {
                    current = current.left;
                }
                // Nodes are on opposite sides or current is one of the nodes
                // In either case, current is the LCA
                else {
                    return current;
                }
            }
            
            return null;
        }
    }

    /**
     * Definition for a binary tree node.
     */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}