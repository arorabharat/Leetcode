public class ZO_Knapsack_2D {
    public class Knapsack {

        public static int knapsack(int[] weight, int[] value, int W) {

            int n = weight.length;

            int[][] dp = new int[n + 1][W + 1];

            for (int i = 1; i <= n; i++) {

                for (int w = 0; w <= W; w++) {

                    // not take item
                    dp[i][w] = dp[i - 1][w];

                    // take item (if possible)
                    if (weight[i - 1] <= w) {

                        dp[i][w] = Math.max(
                                dp[i][w],
                                value[i - 1] + dp[i - 1][w - weight[i - 1]]
                        );
                    }
                }
            }

            return dp[n][W];
        }


        public static void main(String[] args) {

            int[] weight = {1, 2, 3};
            int[] value = {6, 10, 12};

            int W = 5;

            System.out.println(knapsack(weight, value, W));
        }
    }
}
