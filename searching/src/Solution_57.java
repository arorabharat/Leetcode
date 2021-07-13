import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/insert-interval/
 */
class Solution_57 {

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