Move from right to left to optimise psace.
https://www.geeksforgeeks.org/dsa/find-water-in-a-glass/
```java
class Solution {

    static double waterInGlass(int K, int R, int C) {

        double[] dp = new double[R];
        dp[0] = K;

        for (int i = 0; i < R - 1; i++) {

            // traverse backwards to avoid overwriting values
            for (int j = i; j >= 0; j--) {

                if (dp[j] > 1.0) {

                    double overflow = dp[j] - 1.0;

                    dp[j] = overflow / 2.0;
                    dp[j + 1] += overflow / 2.0;

                } else {

                    dp[j] = 0;
                }
            }
        }

        return Math.min(1.0, dp[C - 1]);
    }
}
```