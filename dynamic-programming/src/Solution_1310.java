/**
 * https://leetcode.com/problems/xor-queries-of-a-subarray/
 */
class Solution_1310 {

    /**
     * we keep the xor from index 0 to i.
     * To get the xor of i,j we revert the
     * <p>
     * dp[j] = ]a0, a1, a2, a3 ..ai, .. aj
     * dp[i] = ]a0, a1, a2, a3 ..ai
     * So dp[i] ^ dp[j] we cancel all the terms till i. hence we will get xor of a[i+1].. a[j]
     * To get the answer we just need to xor it with a[i].
     */
    public int[] xorQueries(int[] arr, int[][] queries) {
        int n = arr.length;
        int[] dp = new int[n];
        if (n == 0) {
            return new int[0];
        }
        dp[0] = arr[0];
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] ^ arr[i];
        }
        int m = queries.length;
        int[] ans = new int[m];
        int i = 0;
        for (int[] query : queries) {
            int l = query[0];
            int r = query[1];
            ans[i] = dp[l] ^ dp[r] ^ arr[l];
            i++;
        }
        return ans;
    }
}