import java.util.Deque;
import java.util.LinkedList;

public class Solution_346 {

    static class MovingAverage {

        double avg;
        int size;
        int[] lastNElement;
        int currentIndex;
        int totalStreamElement = 0;

        public MovingAverage(int size) {
            this.size = size;
            this.avg = 0.0;
            this.lastNElement = new int[size];
            this.currentIndex = -1;
        }

        private int nextIndex() {
            return (this.currentIndex + 1) % this.size;
        }

        public double next(int val) {
            int nextIndex = nextIndex();
            if (totalStreamElement < size) {
                lastNElement[nextIndex] = val;
                avg = (avg * totalStreamElement + val) / (totalStreamElement + 1);
            } else {
                int prevVal = lastNElement[nextIndex];
                lastNElement[nextIndex] = val;
                avg = (avg * size - prevVal + val) / size;
            }
            this.currentIndex = nextIndex;
            totalStreamElement++;
            return avg;
        }
    }

    /**
     * Circular array
     */
    static class MovingAverage2 {

        int size, head = 0, windowSum = 0, count = 0;
        int[] queue;

        public MovingAverage2(int size) {
            this.size = size;
            queue = new int[size];
        }

        public double next(int val) {
            ++count;
            int tail = (head + 1) % size;
            windowSum = windowSum - queue[tail] + val;
            head = (head + 1) % size;
            queue[head] = val;
            return windowSum * 1.0 / Math.min(size, count);
        }
    }

    /**
     * Deque approach
     */
    class MovingAverage3 {

        int size, windowSum = 0, count = 0;
        Deque<Integer> queue = new LinkedList<>();

        public MovingAverage3(int size) {
            this.size = size;
        }

        public double next(int val) {
            ++count;
            queue.add(val);
            int tail = (count > size && !queue.isEmpty()) ? queue.poll() : 0;
            windowSum = windowSum - tail + val;
            return windowSum * 1.0 / Math.min(size, count);
        }
    }

}