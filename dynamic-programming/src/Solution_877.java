/**
 * https://leetcode.com/problems/stone-game/
 */
class Solution_877 {

    private int[][] memo;

    /**
     * this method return the maximum sum a person who make the first move can get making alternative move
     * Case 1 : If I pick left then
     * we can get the maximum for the remaining range which would go the opponent
     * Hence I would get sum - maximum for remaining range.
     * Case 2 : If I pick right then
     * we can get the maximum for the remaining range which would go the opponent
     * Hence I would get sum - maximum for remaining range
     */
    private int _stoneGame(int[] piles, int s, int e, int sum) {
        if (s < e) {
            if (memo[s][e] != 0) {
                return memo[s][e];
            }
            int leftPick = _stoneGame(piles, s + 1, e, sum - piles[s]);
            int rightPick = _stoneGame(piles, s, e - 1, sum - piles[e]);
            if (sum - leftPick > leftPick) {
                memo[s][e] = sum - leftPick;
                return sum - leftPick;
            }
            if (sum - rightPick > rightPick) {
                memo[s][e] = sum - rightPick;
                return sum - rightPick;
            }
        }
        return piles[s];
    }

    public boolean stoneGame(int[] piles) {
        int sum = 0;
        for (int p : piles) {
            sum = sum + p;
        }
        int n = piles.length;
        memo = new int[501][501];
        return sum - memo[1][n - 1] > memo[1][n - 1] || sum - memo[0][n - 2] > memo[0][n - 2]; // pick left or pick right
    }
}
