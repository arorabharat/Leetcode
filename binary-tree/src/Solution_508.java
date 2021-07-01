import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution_508 {
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


    public int preorder(TreeNode root, Map<Integer, Integer> sumFreq) {
        if (root == null) return 0;
        int left = preorder(root.left, sumFreq);
        int right = preorder(root.right, sumFreq);
        int sum = left + root.val + right;
        sumFreq.put(sum, sumFreq.getOrDefault(sum, 0) + 1);
        return sum;
    }

    public int[] findFrequentTreeSum(TreeNode root) {
        Map<Integer, Integer> sumFreq = new HashMap<>();
        preorder(root, sumFreq);
        int maxFreq = 0;
        for (int key : sumFreq.keySet()) {
            if (sumFreq.get(key) > maxFreq) {
                maxFreq = sumFreq.get(key);
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int key : sumFreq.keySet()) {
            if (sumFreq.get(key) == maxFreq) {
                list.add(key);
            }
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}