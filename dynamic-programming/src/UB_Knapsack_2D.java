public class UB_Knapsack_2D {
    public class UnboundedKnapsack {

        public static int knapsack(int[] weight, int[] value, int W) {

            int n = weight.length;

            int[][] dp = new int[n + 1][W + 1];

            for (int i = 1; i <= n; i++) {

                for (int w = 0; w <= W; w++) {

                    // do not take item
                    dp[i][w] = dp[i-1][w];

                    // take item (can reuse)
                    if (weight[i-1] <= w) {

                        dp[i][w] = Math.max(
                                dp[i][w],
                                value[i-1] + dp[i][w - weight[i-1]]
                        );
                    }
                }
            }

            return dp[n][W];
        }


        public static void main(String[] args) {

            int[] weight = {2,3};
            int[] value = {5,7};

            int W = 7;

            System.out.println(knapsack(weight, value, W));
        }
    }
}
