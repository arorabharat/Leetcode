/**
 * https://leetcode.com/problems/missing-number/
 *
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

    /**
     * To prevent integer overflow in sum
     */
    public int missingNumber2(int[] nums) {
        int n = nums.length;
        int diff = 0;
        for (int i = 0; i < n; i++) {
            diff = diff + (i + 1) - nums[i];
        }
        return diff;
    }

    /**
     * try bit manipulation
     * Only number which will not cancel itself is the number which do not exists.
     */
    public int missingNumber3(int[] nums) {
        int n = nums.length;
        int xor = 0;
        for (int i = 0; i < n; i++) {
            xor = xor ^ (i + 1) ^ nums[i];
        }
        return xor;
    }
}
