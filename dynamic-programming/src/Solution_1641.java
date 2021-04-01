/**
 * https://leetcode.com/problems/count-sorted-vowel-strings/
 */
class Solution_1641 {

    public int countVowelStrings(int n) {
        int[] dp = new int[5];
        for (int i = 0; i < 5; i++) {
            dp[i] = 5 - i;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 3; j >= 0; j--) {
                dp[j] = dp[j] + dp[j + 1];
            }
        }
        return dp[0];
    }
}