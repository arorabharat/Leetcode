/**
 * https://leetcode.com/problems/missing-number/
 */
class Solution_268 {

    public int missingNumber(int[] nums) {
        int n = nums.length;
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        int expectedSum = n * (n + 1) / 2;
        return expectedSum - actualSum;
    }
}
