public class ZO_Knapsack_1D {
    public class Knapsack1D {

        public static int knapsack(int[] weight, int[] value, int W) {

            int[] dp = new int[W + 1];

            for (int i = 0; i < weight.length; i++) {

                for (int w = W; w >= weight[i]; w--) {

                    dp[w] = Math.max(
                            dp[w],
                            value[i] + dp[w - weight[i]]
                    );
                }
            }

            return dp[W];
        }


        public static void main(String[] args) {

            int[] weight = {1,2,3};
            int[] value = {6,10,12};

            int W = 5;

            System.out.println(knapsack(weight, value, W));
        }
    }
}
