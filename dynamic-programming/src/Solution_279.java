import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/perfect-squares/
 */
class Solution_279 {


    class Solution1 {

        Map<String, Integer> cache = new HashMap<>();

        // least number of squares that make sum
        public int numSquares(int sum) {
            return _numSquares(sum, 1);
        }

        private int _numSquares(int sum, int start) {
            if (sum < 0) return Integer.MAX_VALUE;
            if (sum == 0) return 0;
            String key = sum + "," + start;
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            int minSquares = Integer.MAX_VALUE;
            for (int i = start; i <= sum; i++) {
                int remainingSum = sum - i * i;
                if (remainingSum < 0) break;
                int takeSquareMinWays = _numSquares(remainingSum, start);
                int doNotTakeSquareMinWays = _numSquares(sum, start + 1);
                minSquares = Math.min(minSquares, takeSquareMinWays);
                minSquares = Math.min(minSquares, doNotTakeSquareMinWays);
            }


            if (minSquares == Integer.MAX_VALUE) {
                return minSquares;
            } else {
                minSquares++;
            }
            cache.put(key, minSquares);
            return minSquares;
        }
    }


    class Solution2 {
        public int numSquares(int n) {
            int[] dp = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                dp[i] = i;
            }
            for (int i = 2; i <= n; i++) {
                for (int j = 1; (i - j * j) >= 0; j++) {
                    dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
                }
            }
            return dp[n];
        }
    }
}
