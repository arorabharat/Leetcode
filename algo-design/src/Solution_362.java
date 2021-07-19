import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/design-hit-counter/
 *
 * @link Solution_599
 * @link Solution_1001
 * @link Solution_1788
 */
class Solution_362 {

    static class HitCounter {

        private static final int WINDOW_SIZE = 300;
        Deque<Integer> hitTimestamps;

        public HitCounter() {
            this.hitTimestamps = new LinkedList<>();
        }

        public void hit(int timestamp) {
            this.hitTimestamps.addFirst(timestamp);
            while (!this.hitTimestamps.isEmpty() && (this.hitTimestamps.getFirst() - this.hitTimestamps.getLast() >= WINDOW_SIZE)) {
                this.hitTimestamps.removeLast();
            }
        }

        public int getHits(int timestamp) {
            while (!this.hitTimestamps.isEmpty() && (timestamp - this.hitTimestamps.getLast() >= WINDOW_SIZE)) {
                this.hitTimestamps.removeLast();
            }
            return this.hitTimestamps.size();
        }
    }
}


