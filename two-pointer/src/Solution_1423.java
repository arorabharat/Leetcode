class Solution_1423 {

    int cyclicDecrement(int i, int n) {
        i--;
        return (n + i) % n;
    }

    /**
     * We can only remove the elements from the end.
     * So, to get the optimal sum we could remove X elements from left and Y elements from right.
     * X + Y = k and sum should be optimal . it is classic sliding window problem.
     * We could make the problem difficult by making the elements negative and allowing to choose less than k cards
     */
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int sum = 0;
        for (int i = 0; i < k && i < n; i++) {
            sum = sum + cardPoints[i];
        }
        if (n <= k) {
            return sum;
        }
        int i = 0;
        int j = k - 1;
        int maxAns = sum;
        for (int c = 0; c < k; c++) {
            i = cyclicDecrement(i, n);
            sum = sum + cardPoints[i] - cardPoints[j];
            maxAns = Math.max(maxAns, sum);
            j = cyclicDecrement(j, n);
        }
        return maxAns;
    }

}