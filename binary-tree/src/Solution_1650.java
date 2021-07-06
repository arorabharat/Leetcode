import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iii/
 * https://leetcode.jp/problemdetail.php?id=1650
 * Lowest common ancestor where parent is given with the node
 */
class Solution_1650 {

    /**
     * TO find the lowest common ancestor in in case the parent is given add all the ancestor of the p in a set.
     * then for each ancestor of q check if the ancestor is also ancestor of p.
     */
    public Node lowestCommonAncestor(Node p, Node q) {
        Set<Node> pAncestors = new HashSet<>();
        while (p != null) {
            pAncestors.add(p);
            p = p.parent;
        }
        while (q != null) {
            if (pAncestors.contains(q)) {
                return q;
            }
            q = q.parent;
        }
        return null;
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
}
