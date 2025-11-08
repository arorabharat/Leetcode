/**
 * https://leetcode.com/problems/climbing-stairs/
 */
class Solution_70 {

    // recursive
    class Approach_1 {

        public int climbStairs(int n) {
            if (n < 0) {
                return 0;
            } else if (n == 0 || n == 1) {
                return 1;
            } else {
                return climbStairs(n - 1) + climbStairs(n - 2);
            }
        }
    }

    class Approach_2 {

        public int climbStairs(int n) {
            if (n < 0) {
                return 0;
            } else if (n == 0 || n == 1) {
                return 1;
            }
            int pc2 = 1;
            int pc1 = 1;
            int pc = 0;
            for (int i = 2; i <= n; i++) {
                pc = pc1 + pc2;
                pc2 = pc1;
                pc1 = pc;
            }
            return pc;
        }
    }

    class Approach_3 {
        /**
         * dp[i] represent the number of ways to reach the ith index;
         * ith index can be reached from the last or last second index only
         * Time Complexity :  O( N )
         * Space Complexity :  O( N )
         */
        public int climbStairs(int n) {
            if (n <= 2) return n;
            int[] dp = new int[n];
            dp[0] = 1;
            dp[1] = 2;
            for (int i = 2; i < n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            return dp[n - 1];
        }

    }

    class Approach_4 {

        /**
         * Reducing space complexity.
         * lastSecond represent the number of ways to reach the lastSecond traversed index;
         * last represent the number of ways to reach the last traversed index;
         * Time Complexity :  O( N )
         * Space Complexity :  O( 1 )
         */
        public int climbStairs1(int n) {
            if (n <= 2) return n;
            int lastSecond = 1;
            int last = 2;
            for (int i = 2; i < n; i++) {
                int lastSecondTemp = lastSecond;
                int lastTemp = last;
                lastSecond = lastTemp;
                last = lastSecondTemp + lastTemp;
            }
            return last;
        }
    }

}