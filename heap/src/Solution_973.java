import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/k-closest-points-to-origin/
 * https://github.com/arorabharat/Leetcode/tree/main/heap/src/Solution_973.java
 */
class Solution_973 {

    private static int comparator(int[] point) {
        return -1 * (int) (Math.pow(point[0], 2) + Math.pow(point[1], 2));
    }

    /**
     * Make a max heap and drop the first element if size is greater than k
     */
    public int[][] kClosest(int[][] points, int k) {

        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(Comparator.comparingInt(Solution_973::comparator));

        for (int[] point : points) {
            maxHeap.add(point);
            if (maxHeap.size() > k) {
                maxHeap.remove();
            }
        }

        int[][] ans = new int[k][2];
        int i = 0;
        while (!maxHeap.isEmpty()) {
            int[] point = maxHeap.remove();
            ans[i] = point;
            i++;
        }
        return ans;
    }
}
