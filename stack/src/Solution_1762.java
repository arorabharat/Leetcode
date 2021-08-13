import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/buildings-with-an-ocean-view/
 */
class Solution_1762 {

    public int[] findBuildings(int[] heights) {
        int maxHeight = 0;
        Deque<Integer> q = new LinkedList<>();
        int n = heights.length;
        for (int i = n - 1; i >= 0; i--) {
            if (heights[i] > maxHeight) {
                q.addFirst(i);
            }
            maxHeight = Math.max(maxHeight, heights[i]);
        }
        int k = q.size();
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = q.remove();
        }
        return ans;
    }

}