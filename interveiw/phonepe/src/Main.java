/**
 * There is a network iof roads that look like a binary tree.. There is a delivery van located at the root node..
 * and it has to deliver parcels at some of the nodes, and then come back to the root node.
 * <p>
 * tell the minimum time required to deliver all the items and return to the root.
 * <p>
 * 0
 * / \
 * 1   2
 * / \ / \
 * 4  5 3  6
 * <p>
 * [2, 4, 5]
 * <p>
 * OP - 8;
 * 0 -> 1 -> 4 -> 1 -> 5 -> 1 -> 0 -> 2 -> 0
 * 0 -> 2 -> 0 -> 1 -> 4 -> 1 -> 5 -> 1 -> 0
 * <p>
 * <p>
 * 0
 * / \
 * (2)1   2
 * / \ / \
 * (2)4(2)  5 3  6
 * / \
 * 7    8 (2)
 * \
 * 9
 * <p>
 * <p>
 * <p>
 * 4,5,7,8,9
 * <p>
 * 0
 * / \
 * 1   2
 * / \ / \
 * 4  5 3  6
 * <p>
 * 4
 * Ans - 4
 * <p>
 * Tc - O(N)
 * SC O(H) ~N
 * <p>
 * class TreeNode {
 * TreeNode left;
 * TreeNode right;
 * boolean isDeliveryRequired;
 * }
 * <p>
 * public int minTime(TreeNode root) {
 * <p>
 * }
 * <p>
 **/


class TreeNode {
    TreeNode left;
    TreeNode right;
    boolean isDeliveryRequired;
}

class Main {



    static class BinaryTree {

        int totalDeliveryTime;

        public int minTime(TreeNode root) {
            this.totalDeliveryTime = 0;
            ;
            dfsTraversal(root);
            return this.totalDeliveryTime;
        }

        boolean dfsTraversal(TreeNode root) {
            if (root == null) {
                return false;
            }
            boolean leftBranch = dfsTraversal(root.left);
            boolean rightBranch = dfsTraversal(root.right);
            if (leftBranch) {
                totalDeliveryTime = totalDeliveryTime + 2;
            }
            if (rightBranch) {
                totalDeliveryTime = totalDeliveryTime + 2;
            }
            if (root.isDeliveryRequired || leftBranch || rightBranch) {
                return true;
            }
            return false;
        }
    }

    public static void main(String args[]) throws Exception {
        TreeNode zero = new TreeNode();
        TreeNode one = new TreeNode();
        TreeNode two = new TreeNode();
        two.isDeliveryRequired = true;
        TreeNode four = new TreeNode();
        TreeNode five = new TreeNode();
        four.isDeliveryRequired = true;
        five.isDeliveryRequired = true;
        TreeNode three = new TreeNode();
        TreeNode six = new TreeNode();
        one.left = four;
        one.right = five;
        two.left = three;
        two.right = six;
        zero.left = one;
        zero.right = two;

        BinaryTree binaryTree = new BinaryTree();
        System.out.println(binaryTree.minTime(zero));
    }
}



