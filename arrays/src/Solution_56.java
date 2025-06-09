import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * https://github.com/arorabharat/Leetcode/tree/main/arrays/src/Solution_56.java
 * https://leetcode.com/problems/merge-intervals/
 */
class Solution_56 {


    class Solution2 {

        public int[][] merge(int[][] intervalsArray) {
            List<Interval> intervals = new ArrayList<>();
            for (int[] ints : intervalsArray) {
                int start = ints[0];
                int end = ints[1];
                intervals.add(new Interval(start, end));
            }
            intervals.sort((i1, i2) -> {
                if (i1.start == i2.start) {
                    return Integer.compare(i1.end, i2.end);
                }
                return Integer.compare(i1.start, i2.start);
            });
            Interval currInterval = null;
            List<Interval> mergedIntervals = new ArrayList<>();
            for (Interval interval : intervals) {
                if (currInterval == null) {
                    currInterval = interval;
                } else if (currInterval.end >= interval.start) {
                    currInterval.end = Math.max(currInterval.end, interval.end);
                } else {
                    mergedIntervals.add(currInterval);
                    currInterval = interval;
                }
            }
            mergedIntervals.add(currInterval);
            int[][] mergedArr = new int[mergedIntervals.size()][2];
            for (int i = 0; i < mergedIntervals.size(); i++) {
                mergedArr[i][0] = mergedIntervals.get(i).start;
                mergedArr[i][1] = mergedIntervals.get(i).end;
            }
            return mergedArr;
        }

        class Interval {
            int start;
            int end;

            public Interval(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }


    class Solution1 {

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
}

