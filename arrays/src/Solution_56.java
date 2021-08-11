import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * https://leetcode.com/problems/merge-intervals/
 */
class Solution_56 {

    private static final int END = 1;
    private static final int START = 0;

    private List<int[]> mergeTwo(int[] interval1, int[] interval2) {
        List<int[]> list = new ArrayList<>();
        if (interval1[END] < interval2[START]) {
            list.add(interval1);
            list.add(interval2);
        } else {
            list.add(new int[]{interval1[START], Math.max(interval1[END], interval2[END])});
        }
        return list;
    }

    private int[][] convertToArray(List<int[]> list) {
        int[][] ans = new int[list.size()][2];
        return list.toArray(ans);
    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> list = new ArrayList<>();
        for (int[] interval : intervals) {
            if (list.isEmpty()) {
                list.add(interval);
            } else {
                int[] lastInterval = list.remove(list.size() - 1);
                list.addAll(mergeTwo(lastInterval, interval));
            }
        }
        return convertToArray(list);
    }
}

