/**
 * https://leetcode.com/problems/missing-number/
 * @see Solution_41
 * @see Solution_136
 * @see Solution_287
 * @see Solution_765
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
