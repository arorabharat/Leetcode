/**
 * https://leetcode.com/problems/house-robber/
 */
class Solution_198 {
    /**
     * Recursive approach
     */
    private int _rob(int[] nums, int n) {
        if (n <= 0) {
            return 0;
        }
        return Math.max(nums[n - 1] + _rob(nums, n - 2), _rob(nums, n - 1));
    }

    public int rob(int[] nums) {
        return _rob(nums, nums.length);
    }

    /**
     * Dynamic programming
     * Time Complexity :  O( N )
     * Space Complexity :  O( N )
     */
    public int rob2(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }
        return dp[n - 1];
    }

    /**
     * Dynamic programming 2
     * Time Complexity :  O( N )
     * Space Complexity :  O( 1 )
     */
    public int rob3(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        int a = nums[0];
        int b = Math.max(nums[0], nums[1]);
        int c = 0;
        for (int i = 2; i < n; i++) {
            c = Math.max(nums[i] + a, b);
            a = b;
            b = c;
        }
        return c;
    }

}
