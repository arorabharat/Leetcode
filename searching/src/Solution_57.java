import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/insert-interval/
 * TODO : implement it using the two binary search
 */
class Solution_57 {


    class Solution {

        int[][] intervals;

        // [[1,3],
        // [6,9]]
        //  2 4 6 8 10
        int binarySearch(int s, int e, int target) {
            if (s < e) {
                int m = (s + e) / 2;
                if (target == intervals[m][0]) {
                    return m;
                } else if (intervals[m][0] < target) {
                    return binarySearch(m + 1, e, target);
                } else {
                    return binarySearch(s, m - 1, target);
                }
            }
            return s;
        }

        public int[][] insert(int[][] intervals, int[] newInterval) {
            List<int[]> resultedInterval = new ArrayList<>();
            this.intervals = intervals;
            int n = intervals.length - 1;
            int newStart = newInterval[0];
            int insertTionIndex = binarySearch(0, n - 1, newStart);
            Deque<int[]> q = new LinkedList<>();
            if(insertTionIndex == -1) {
                q.add(newInterval);
            }
            for (int i = 0 ; i < insertTionIndex; i++) {
                q.add(intervals[i]);
            }
            if(!q.isEmpty()) {
                int[] lastInterval = q.peekLast();
                if(newInterval[0] <= lastInterval[1]) {
                    lastInterval[1] = Math.max(newInterval[1] , lastInterval[1]);
                } else {
                    q.add(newInterval);
                }
            } else{
                q.add(newInterval);
            }
            for (int i = insertTionIndex; i < n; i++) {
                int[] lastInterval = q.peekLast();
                if(lastInterval != null && intervals[i][0] <= lastInterval[1]) {
                    lastInterval[1] = Math.max(intervals[i][1] , lastInterval[1]);
                } else {
                    q.add(intervals[i]);
                }
            }
            int rs = q.size();
            int[][] resultArr = new int[rs][2];
            int i = 0;
            for (int[] interval : resultedInterval) {
                resultArr[i] = interval;
            }
            return resultArr;
        }
    }


    /**
     * @see DSA#BINARY_SEARCH
     */
    private int binary_search(int[][] intervals, int s, int e, int k) {
        if (s < e) {
            int m = s + (e + 1 - s) / 2;
            if (intervals[m][0] > k) {
                return binary_search(intervals, s, m - 1, k);
            } else {
                return binary_search(intervals, m, e, k);
            }
        }
        return s;
    }


    private void join(Deque<int[]> q, int[] interval) {
        if (q.isEmpty()) q.addLast(interval);
        int a = q.getLast()[0];
        int b = q.getLast()[1];
        if (b < interval[0]) {
            q.addLast(interval);
        } else {
            q.removeLast();
            int[] m = new int[2];
            m[0] = a;
            m[1] = Math.max(b, interval[1]);
            q.addLast(m);
        }
    }


    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        if (n == 0) {
            int[][] res = new int[1][2];
            res[0][0] = newInterval[0];
            res[0][1] = newInterval[1];
            return res;
        }
        int start = binary_search(intervals, -1, n - 1, newInterval[0]);

        Deque<int[]> q = new LinkedList<>();

        for (int i = 0; i <= start; i++) {
            q.addLast(intervals[i]);
        }

        join(q, newInterval);

        for (int i = start + 1; i < n; i++) {
            join(q, intervals[i]);
        }

        int N = q.size();
        int[][] res = new int[N][2];

        for (int i = 0; i < N; i++) {
            res[i][0] = q.getFirst()[0];
            res[i][1] = q.getFirst()[1];
            q.removeFirst();
        }
        return res;
    }
}