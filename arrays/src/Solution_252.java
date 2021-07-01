import java.util.Arrays;
import java.util.Comparator;

public class Solution_252 {

    /**
     * Sort all the meetings by the start time,
     * Check if the current interval overlap with the previous interval, if it overlap person could not attend all the meetings
     */
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i - 1][1] > intervals[i][0]) {
                return false;
            }
        }
        return true;
    }
}