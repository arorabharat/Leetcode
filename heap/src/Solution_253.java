import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/meeting-rooms-ii/
 */
class Solution_253 {

    /**
     * Time Complexity :  O( N Log N )
     * Space Complexity :  O( N )
     */
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0])); // N Long N , comparator (a, b) -> a[0] - b[0]
        int n = intervals.length;
        if (n < 2) return n;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int maxCount = 0;
        minHeap.add(intervals[0]);
        for (int i = 1; i < n; i++) {
            if (!minHeap.isEmpty() && minHeap.peek()[1] <= intervals[i][0]) {
                minHeap.remove(); // O (1)
            }
            minHeap.add(intervals[i]); // O(log N)
            maxCount = Math.max(maxCount, minHeap.size());
        }
        return maxCount;
    }
}
