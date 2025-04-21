import java.util.LinkedList;
import java.util.Queue;

public class Solution_933 {

    class RecentCounter {

        private final Queue<Integer> time = new LinkedList<>();

        public RecentCounter() {
        }

        public int ping(int t) {
            while (!time.isEmpty() && isOutsideWindowInterval(t, time.peek())) {
                time.remove();
            }
            time.add(t);
            return time.size();
        }

        private boolean isOutsideWindowInterval(int t, int oldest) {
            int WINDOW_SIZE = 3000;
            return (t - oldest) > WINDOW_SIZE;
        }
    }

}
