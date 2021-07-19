import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/top-k-frequent-elements/
 */
class Solution_347 {


    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int n = map.size();
        int[][] freq = new int[n][2];
        int i = 0;
        for (int num : map.keySet()) {
            freq[i][0] = num;
            freq[i][1] = map.get(num);
            i++;
        }
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(Comparator.comparingInt(a -> -a[1]));
        for (int j = 0; j < n; j++) {
            maxHeap.add(freq[j]);
        }
        int[] ans = new int[k];
        for (int j = 0; j < k; j++) {
            int[] front = maxHeap.remove();
            ans[j] = front[0];
        }
        return ans;
    }
}