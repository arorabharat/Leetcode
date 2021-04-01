/**
 * https://leetcode.com/problems/largest-sum-of-averages/
 */
class Solution_813 {

    public double largestSumOfAverages(int[] A, int K) {
        int n = A.length;
        double[][][] dp = new double[n][n][K + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < K; k++) {
                    dp[i][j][k] = -10000000;
                }
            }
        }

        double[] sum = new double[n];
        sum[0] = A[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + A[i];
        }

        for (int k = 1; k <= K; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    if (k == 1) {
                        dp[i][j][k] = ((i > 0) ? (sum[j] - sum[i - 1]) : sum[j]) / (j - i + 1);
                    } else if (j - i + 1 >= k) {
                        for (int w = i; w < j; w++) {
                            double sd = dp[i][w][k - 1] + dp[w + 1][j][1];
                            dp[i][j][k] = Math.max(dp[i][j][k], sd);
                        }
                    }
                }
            }
        }
        return dp[0][n - 1][K];
    }

}