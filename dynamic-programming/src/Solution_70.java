/**
 * https://leetcode.com/problems/climbing-stairs/
 * Problem: You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 */
class Solution_70 {

    /**
     * Approach 1: Recursive (Naive)
     * Time Complexity: O(2^N) - Exponential
     * Space Complexity: O(N) - Recursion stack
     * Note: This is inefficient and will timeout for large n
     */
    class Approach_1 {
        public int climbStairs(int n) {
            if (n <= 1) return 1;
            return climbStairs(n - 1) + climbStairs(n - 2);
        }
    }

    /**
     * Approach 2: Recursive with Memoization (Top-Down DP)
     * Time Complexity: O(N)
     * Space Complexity: O(N) - Memoization array + recursion stack
     */
    class Approach_2 {
        public int climbStairs(int n) {
            int[] memo = new int[n + 1];
            return climbStairsHelper(n, memo);
        }

        private int climbStairsHelper(int n, int[] memo) {
            if (n <= 1) return 1;
            if (memo[n] != 0) return memo[n];
            memo[n] = climbStairsHelper(n - 1, memo) + climbStairsHelper(n - 2, memo);
            return memo[n];
        }
    }

    /**
     * Approach 3: Bottom-Up DP with Array
     * dp[i] represents the number of ways to reach step i
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    class Approach_3 {
        public int climbStairs(int n) {
            if (n <= 2) return n;
            int[] dp = new int[n + 1];
            dp[1] = 1;
            dp[2] = 2;
            for (int i = 3; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            return dp[n];
        }
    }

    /**
     * Approach 4: Space-Optimized DP (Fibonacci-like)
     * Only keep track of last two values
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    class Approach_4 {
        public int climbStairs(int n) {
            if (n <= 2) return n;
            int first = 1;  // ways to reach step 1
            int second = 2; // ways to reach step 2
            for (int i = 3; i <= n; i++) {
                int third = first + second;
                first = second;
                second = third;
            }
            return second;
        }
    }

    /**
     * Approach 5: Matrix Exponentiation
     * Uses matrix multiplication to compute Fibonacci in O(log N) time
     * Time Complexity: O(log N)
     * Space Complexity: O(1)
     * 
     * The recurrence relation can be represented as:
     * [F(n+1)]   [1 1]^n [F(1)]
     * [F(n)  ] = [1 0]   [F(0)]
     * 
     * For climbing stairs: climbStairs(n) = F(n+1) where F is Fibonacci
     * So we raise the matrix to power n to get F(n+1)
     */
    class Approach_5 {
        public int climbStairs(int n) {
            if (n <= 2) return n;
            // Transformation matrix for Fibonacci
            int[][] base = {{1, 1}, {1, 0}};
            // Raise to power n to get F(n+1) in [0][0]
            int[][] result = matrixPower(base, n);
            return result[0][0];
        }

        private int[][] matrixPower(int[][] matrix, int power) {
            int[][] result = {{1, 0}, {0, 1}}; // Identity matrix
            while (power > 0) {
                if (power % 2 == 1) {
                    result = matrixMultiply(result, matrix);
                }
                matrix = matrixMultiply(matrix, matrix);
                power /= 2;
            }
            return result;
        }

        private int[][] matrixMultiply(int[][] a, int[][] b) {
            int[][] c = new int[2][2];
            c[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
            c[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
            c[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
            c[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
            return c;
        }
    }

    /**
     * Approach 6: Binets Formula (Mathematical)
     * Uses the closed-form formula for Fibonacci numbers
     * F(n) = (phi^n - psi^n) / sqrt(5)
     * where phi = (1 + sqrt(5)) / 2 and psi = (1 - sqrt(5)) / 2
     * 
     * Time Complexity: O(log N) - due to power operation
     * Space Complexity: O(1)
     * Note: May have precision issues for very large n
     */
    class Approach_6 {
        public int climbStairs(int n) {
            if (n <= 2) return n;
            double sqrt5 = Math.sqrt(5);
            double phi = (1 + sqrt5) / 2;
            double psi = (1 - sqrt5) / 2;
            // For climbing stairs, we need F(n+1) where F is Fibonacci
            // Since F(1)=1, F(2)=1, F(3)=2, F(4)=3...
            // climbStairs(n) = F(n+1)
            return (int) Math.round((Math.pow(phi, n + 1) - Math.pow(psi, n + 1)) / sqrt5);
        }
    }

    /**
     * Approach 7: Iterative with Better Variable Names
     * Cleaner version of space-optimized approach
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    class Approach_7 {
        public int climbStairs(int n) {
            if (n <= 2) return n;
            int prev = 1;  // ways for step n-2
            int curr = 2;  // ways for step n-1
            for (int i = 3; i <= n; i++) {
                int next = prev + curr;
                prev = curr;
                curr = next;
            }
            return curr;
        }
    }

}