import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/unique-binary-search-trees/
 */
class Solution_96 {

    /**
     * ========================================
     */

    Map<Integer, Integer> resultsCache = new HashMap<>();

    public int numTrees4(int n) {
        if (n < 0) return -1;
        if (n == 0 || n == 1) return 1;
        if (resultsCache.containsKey(n)) return resultsCache.get(n);
        int count = 0;
        for (int i = 0; i < n; i++) {
            count = count + numTrees4(n - 1 - i) * numTrees(i);
        }
        resultsCache.put(n, count);
        return count;
    }

    /**
     * ========================================
     */

    /**
     * Number of unique structural BST could calculated by the following algo.
     * Each BST will have some root, lets fix all the possible root one by one from 1 to n ( for i loop )
     * For each given root, it is deterministic that the node smaller than i will go ont the left
     * and greater than i will go on the right side. Now, we need to calculate the BST for the left and right tree.
     * Dp[i]  represent the number of unique tree that can created given i unique values
     * Example : Calculating for size 3 unique tree
     * [left,right,root]
     * [0,2,1]
     * [1,1,2]
     * [2,0,3]
     */
    public int numTrees(int n) {
        if (n < 2) return n;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 0);
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                int left = j - 1;
                int right = i - j;
                dp[i] = dp[i] + dp[left] * dp[right];
            }
        }
        return dp[n];
    }
}