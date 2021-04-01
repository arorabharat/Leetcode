/**
 * https://leetcode.com/problems/guess-number-higher-or-lower-ii/
 */
class Solution_375 {

    public int getMoneyAmount(int n) {
        if (n < 3) return n - 1;
        int[][] moneyAmount = new int[n + 2][n + 2];
        for (int i = 1; i < n; i++) {
            moneyAmount[i][i + 1] = i;
        }
        for (int len = 3; len <= n; len++) {
            for (int j = 1; j <= n - len + 1; j++) {
                int temp = Integer.MAX_VALUE;
                for (int k = j; k < j + len; k++) {
                    temp = Math.min(temp, k + Math.max(moneyAmount[j][k - 1], moneyAmount[k + 1][j + len - 1]));
                }
                moneyAmount[j][j + len - 1] = temp;
            }
        }
        return moneyAmount[1][n];
    }
}
