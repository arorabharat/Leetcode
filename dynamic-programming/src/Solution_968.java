import java.util.HashMap;
import java.util.Map;

class Solution_968 {

    Map<TreeNode, Integer> map;

    private int _minCameraCover(TreeNode root) {
        if (map.containsKey(root)) {
            return map.get(root);
        }
        TreeNode leftChild = root.left;
        TreeNode leftLeftChild = (leftChild != null) ? leftChild.left : null;
        TreeNode leftRightChild = (leftChild != null) ? leftChild.right : null;
        TreeNode rightChild = root.right;
        TreeNode rightLeftChild = (rightChild != null) ? rightChild.left : null;
        TreeNode rightRightChild = (rightChild != null) ? rightChild.right : null;
        int leftChildTaken = _minCameraCover(leftLeftChild) + _minCameraCover(leftRightChild) + _minCameraCover(rightChild);
        int rightChildTaken = _minCameraCover(leftChild) + _minCameraCover(rightLeftChild) + _minCameraCover(rightRightChild);
        int taken = 1 + _minCameraCover(leftLeftChild) + _minCameraCover(leftRightChild) + _minCameraCover(rightLeftChild) + _minCameraCover(rightRightChild);
        int notTaken = 1 + Math.min(leftChildTaken, rightChildTaken);
        map.put(root, Math.min(taken, notTaken));
        return map.get(root);
    }

    public int minCameraCover(TreeNode root) {
        map = new HashMap<>();
        map.put(null, 0);
        return _minCameraCover(root);
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
