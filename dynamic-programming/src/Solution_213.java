/**
 * https://leetcode.com/problems/house-robber-ii/
 */
class Solution_213 {

    // f(N) = f( c[n-1] + f( n - 2) , f(N -1) )
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[1], nums[0]);
        for (int i = 2; i < n; i++) {
            dp[i] = +Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }
        int secondLast = dp[n - 2];
        dp[0] = 0;
        dp[1] = nums[1];
        for (int i = 2; i < n; i++) {
            dp[i] = +Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }
        return Math.max(secondLast, dp[n - 1]);
    }

    public int rob2(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        int a = nums[0];
        int b = Math.max(nums[1], nums[0]);
        int c = 0;
        for (int i = 2; i < n; i++) {
            c = Math.max(nums[i] + a, b);
            a = b;
            b = c;
        }
        int secondLast = a;
        a = 0;
        b = nums[1];
        for (int i = 2; i < n; i++) {
            c = Math.max(nums[i] + a, b);
        }
        return Math.max(secondLast, c);
    }
}
