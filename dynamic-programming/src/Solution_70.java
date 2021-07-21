/**
 * https://leetcode.com/problems/climbing-stairs/
 */
class Solution_70 {

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