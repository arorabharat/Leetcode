import java.util.Arrays;
import java.util.Comparator;

public class Solution_435 {

    class Solution {

        Comparator<int[]> endBasedIndexComparator() {
            return new Comparator<int[]>() {
                @Override
                public int compare(int[] a, int[] b) {
                    int compResult = Integer.compare(a[1], b[1]);
                    if (compResult == 0) {
                        return Integer.compare(a[0], b[0]);
                    }
                    return compResult;
                }
            };
        }

        public int eraseOverlapIntervals(int[][] intervals) {
            Arrays.sort(intervals, endBasedIndexComparator());
            int removalCount = 0;
            int prevEnd = Integer.MIN_VALUE;
            for (int[] pair : intervals) {
                int start = pair[0];
                int end = pair[1];
                if (start < prevEnd) {
                    removalCount++;
                } else {
                    prevEnd = end;
                }
            }
            return removalCount;
        }
    }
}
