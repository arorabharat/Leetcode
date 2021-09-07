import java.util.Arrays;
import java.util.Stack;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-bst/
 */
class Solution_449 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String leftTree = serialize(root.left);
        String rightTree = serialize(root.right);
        sb.append(root.val);
        if (!leftTree.isBlank() || !rightTree.isBlank()) {
            sb.append("(").append(leftTree).append(")");
        }
        if (!rightTree.isBlank()) {
            sb.append("(").append(rightTree).append(")");
        }
        return sb.toString();
    }

    int getIntegerValue(char[] data, int s, int e) {
        StringBuilder sb = new StringBuilder();
        while (s<=e) {
            sb.append(data[s]);
            s++;
        }
        return Integer.parseInt(sb.toString());
    }

    public TreeNode deserialize(char[] data, int s, int e, int[] clos) {
        if (s > e) {
            return null;
        }
        int i = s;
        while ( i<=e && data[i] != '(' ) {
            i++;
        }
        TreeNode root = new TreeNode(getIntegerValue(data,s,i));
        if(i == e) {
            return root;
        }
        root.left = deserialize(data,i+1,clos[i]-1,clos);
        root.right = deserialize(data,clos[i]+2,e-1,clos);
        return root;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Stack<Integer> stack = new Stack<>();
        int n = data.length();
        char[] dataArr = data.toCharArray();
        int[] clos = new int[n];
        // O(N)
        Arrays.fill(clos, -1);
        // O(N)
        for (int i = n - 1; i >= 0; i--) {
            if (dataArr[i] == ')') {
                stack.push(i);
            } else if (dataArr[i] == '(') {
                clos[i] = stack.pop();
            }
        }
        return deserialize(dataArr, 0, n - 1, clos);
    }
}
