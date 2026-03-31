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

    class Approach2 {

        private int robRange(int[] nums, int s, int e) {
            if (s == e) {
                return nums[s];
            }
            if (e == s + 1) {
                return Math.max(nums[s], nums[e]);
            }
            int p1 = Math.max(nums[s], nums[e]);
            int p2 = nums[s];
            for (int i = s + 2; i < e; i++) {
                int curr = Math.max(nums[i] + p2, nums[i]);
                p2 = p1;
                p1 = Math.max(curr, p1);
            }
            return p1;
        }

        public int rob(int[] nums) {
            int length = nums.length;
            if (length == 1) {
                return nums[0];
            }
            if (length == 2) {
                return Math.max(nums[0], nums[1]);
            }
            int lastNotTaken = robRange(nums, 0, length - 2);
            int lastTaken = robRange(nums, 1, length - 1);
            return Math.max(lastTaken, lastNotTaken);
        }
    }
}
