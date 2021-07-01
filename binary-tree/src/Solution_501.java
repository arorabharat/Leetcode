import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/find-mode-in-binary-search-tree/
 */
class Solution_501 {

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

    private void inorderTraversal(TreeNode root, List<Integer> sortedList) {
        if (root != null) {
            inorderTraversal(root.left, sortedList);
            sortedList.add(root.val);
            inorderTraversal(root.right, sortedList);
        }
    }

    public int[] findMode(TreeNode root) {
        List<Integer> sortedList = new ArrayList<>();
        inorderTraversal(root, sortedList);
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (Integer x : sortedList) {
            freqMap.put(x, freqMap.getOrDefault(x, 0) + 1);
        }
        // find the max frequency / mode
        int maxFrequency = 0;
        for (Integer x : freqMap.keySet()) {
            if (freqMap.get(x) > maxFrequency) {
                maxFrequency = freqMap.get(x);
            }
        }
        // find element which have the max frequency
        List<Integer> modeList = new ArrayList<>();
        for (Integer x : freqMap.keySet()) {
            if (freqMap.get(x) == maxFrequency) {
                modeList.add(x);
            }
        }
        // convert to mode list to array
        int[] modeArray = new int[modeList.size()];
        for (int i = 0; i < modeList.size(); i++) {
            modeArray[i] = modeList.get(i);
        }
        return modeArray;
    }
}