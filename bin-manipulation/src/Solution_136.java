/**
 * https://leetcode.com/problems/single-number/
 *
 * @see Solution_137
 * @see Solution_268
 * @see Solution_389
 */
class Solution_136 {

    public int singleNumber(int[] nums) {
        int xor = 0;
        for (int num : nums) {
            xor = xor ^ num;
        }
        return xor;
    }
}
