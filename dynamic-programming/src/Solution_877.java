/**
 * https://leetcode.com/problems/stone-game/
 */
class Solution_877 {

    private int[][] memo;

    private int _stoneGame(int[] piles, int s, int e, int sum) {
        if (s <= e) {
            if (memo[s][e] != 0) {
                return memo[s][e];
            }
            int leftPick = _stoneGame(piles, s + 1, e, sum - piles[s]);
            int rightPick = _stoneGame(piles, s, e - 1, sum - piles[e]);
            if (piles[s] + leftPick > sum - piles[s] - leftPick) {
                memo[s][e] = piles[s] + leftPick;
                return piles[s] + leftPick;
            }
            if (piles[e] + rightPick > sum - piles[e] - rightPick) {
                memo[s][e] = piles[e] + rightPick;
                return piles[e] + rightPick;
            }
        }
        return 0;
    }

    public boolean stoneGame(int[] piles) {
        int sum = 0;
        for (int p : piles) {
            sum = sum + p;
        }
        int n = piles.length;
        memo = new int[501][501];
        _stoneGame(piles, 0, n - 1, sum);
        return memo[0][n - 1] > memo[1][n - 2] || memo[0][n - 1] > memo[0][n - 2]; // pick left or pick right
    }
}
