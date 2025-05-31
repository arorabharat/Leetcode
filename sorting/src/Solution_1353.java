import java.util.Arrays;
import java.util.Comparator;

public class Solution_1353 {


    class Solution {

        private Comparator<int[]> comparator() {
            return new Comparator<>() {
                @Override
                public int compare(int[] a, int[] b) {
                    if (a[0] == b[0]) {
                        return Integer.compare(a[1], b[1]);
                    }
                    return Integer.compare(a[0], b[0]);
                }
            };
        }

        private Comparator<int[]> comparator2() {
            return (a, b) -> {
                if (a[0] == b[0]) {
                    return Integer.compare(a[1], b[1]);
                }
                return 0;
            };
        }

        public int maxEvents(int[][] events) {
            Arrays.sort(events, comparator());
            int eventAttended = 0;
            int lastEventDay = 0;
            for (int[] event : events) {
                int startDay = event[0];
                int endDay = event[1];
                if (lastEventDay < startDay) {
                    lastEventDay = startDay;
                    eventAttended++;
                } else {
                    if (lastEventDay < endDay) {
                        lastEventDay++;
                        eventAttended++;
                    }
                }
            }
            return eventAttended;
        }
    }
}
