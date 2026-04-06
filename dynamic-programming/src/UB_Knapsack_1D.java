public class UB_Knapsack_1D {

    public class UnboundedKnapsack1D {

        public static int knapsack(int[] weight, int[] value, int W) {

            int[] dp = new int[W + 1];

            for (int i = 0; i < weight.length; i++) {

                for (int w = weight[i]; w <= W; w++) {

                    dp[w] = Math.max(
                            dp[w],
                            value[i] + dp[w - weight[i]]
                    );
                }
            }

            return dp[W];
        }


        public static void main(String[] args) {

            int[] weight = {2, 3};
            int[] value = {5, 7};

            int W = 7;

            System.out.println(knapsack(weight, value, W));
        }
    }
}
