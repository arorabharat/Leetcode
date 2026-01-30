import java.util.*;

/**
 * https://leetcode.com/problems/meeting-rooms-ii/
 */
class Solution_253 {


    class Approach_2 {

        public int minMeetingRooms(int[][] intervals) {
            Arrays.sort(intervals,
                    Comparator.comparingInt((int[] x) -> x[0]).thenComparing(x -> x[1]));
            PriorityQueue<Integer> rooms = new PriorityQueue<>();
            Arrays.stream(intervals).forEach(
                    interval -> {
                        if(rooms.isEmpty()) {
                            rooms.add(interval[1]);
                        } else {
                            boolean isAvailable = false;
                            for (int roomEndTime : rooms) {
                                if (roomEndTime <= interval[0]) {
                                    isAvailable = true;
                                    break;
                                }
                            }
                            if(!isAvailable) {
                                rooms.add(interval[1]);
                            } else {
                                rooms.remove();
                                rooms.add(interval[1]);
                            }
                        }
                    }
            );
            return rooms.size();
        }
    }

    class Approach_1 {

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
}
