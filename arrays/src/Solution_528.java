import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Solution_528 {

    static class Solution {

        private final int[] prefixSum;
        private final int n;
        private final int totalSum;
        private final Random random;

        public Solution(int[] w) {
            n = w.length;
            prefixSum = new int[n];
            prefixSum[0] = w[0];
            IntStream.range(1, n).forEach(i -> {
                prefixSum[i] = prefixSum[i - 1] + w[i];
            });
            totalSum = Arrays.stream(w).sum();
            random = new Random();
        }

        private int binarySearch(int key) {
            int s = 0;
            int e = n - 1;
            while (s < e) {
                int m = s + (e - s) / 2;
                if (prefixSum[m] <= key) {
                    s = m + 1;
                } else {
                    e = m;
                }
            }
            return s;
        }

        public int pickIndex() {
            int randomNumber = random.nextInt(totalSum);
            return binarySearch(randomNumber);
        }
    }
}
