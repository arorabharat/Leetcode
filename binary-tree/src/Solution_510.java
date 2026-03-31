/**
 * https://leetcode.com/problems/inorder-successor-in-bst-ii/
 */
class Solution_510 {

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

    ;

    class Solution {


        public Node inorderSuccessor(Node node) {
            if (node == null) {
                return null;
            }
            Node tr = node.right;
            while (tr != null && tr.left != null) {
                tr = tr.left;
            }
            if (tr != null) {
                return tr;
            }
            tr = node.parent;
            if (tr != null && tr.left == node) {
                return tr;
            }
            while (tr != null && tr.val < node.val) {
                tr = tr.parent;
            }
            return tr;
        }
    }
}
