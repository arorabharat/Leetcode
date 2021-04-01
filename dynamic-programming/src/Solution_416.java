/**
 * 0-1 knapsack problem
 * https://leetcode.com/problems/partition-equal-subset-sum/
 * https://www.geeksforgeeks.org/subset-sum-problem-dp-25/
 */
class Solution_416 {

    /**
     * recursive step -
     * Case 1 - we take the variable in the sum, then we have to make remaining sum using the remaining nums
     * Case 2 - we do not include the current number in the sum, then we have to find sum in remaining nums
     * base case - it is not possible to make sum if sum is less than zero or there are no numbers left
     * if the current num is equal to sum then it is possible to make the sum by including the number in the sum
     */
    public boolean _canPartition(int[] nums, int n, int sum) {
        if (n < 0 || sum < 0) return false;
        if (nums[n] == sum) return true;
        return _canPartition(nums, n - 1, sum) || _canPartition(nums, n - 1, sum - nums[n]);
    }

    public boolean canPartition(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) return false;
        sum = sum / 2;
        return _canPartition(nums, n - 1, sum);
    }

    /**
     * base step -
     * Case 1 - if we have only one number then we can make sum only when number is equal to sum
     * Case 2 - if we have more than one number and number is equal to the sum then we can make the sum
     * recursive step - we have more then one numbers ,
     * then we can make the sum by not taking the number and checking if it is possible to make the sum from remaining numbers
     * or we can make the sum by taking the number and checking if we can make the ( sum - number ) using the remaining numbers
     */
    public boolean canPartition1(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) return false;
        sum = sum / 2;
        boolean[][] dp = new boolean[n][sum + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (i == 0) {
                    dp[i][j] = (nums[i] == j);
                } else {
                    if (j - nums[i] == 0) {
                        dp[i][j] = true;
                    } else if (j - nums[i] > 0) {
                        dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
        }
        return dp[n - 1][sum];
    }
}
