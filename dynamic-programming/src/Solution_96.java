import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/unique-binary-search-trees/
 */
class Solution_96 {


    class Approach_1 {

        final Map<Integer, Integer> preCompute = new HashMap<>();

        public int numTrees(int n) {
            if (n < 0) return -1;
            if (n == 0) return 1;
            if (n == 1) {
                return 1;
            }
            if (preCompute.containsKey(n)) {
                return preCompute.get(n);
            }
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum = sum + numTrees(i) * numTrees(n - 1 - i);
            }
            preCompute.put(n, sum);
            return sum;
        }
    }

    class Approach_2 {

        public int numTrees(int n) {
            int[] dp = new int[n + 1];
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i <= n; i++) {
                for (int j = 0; j < i; j++) {
                    dp[i] = dp[i] + dp[j] * dp[i - 1 - j];
                }
            }
            return dp[n];
        }
    }
}