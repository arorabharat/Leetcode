/**
 * https://leetcode.com/problems/product-of-array-except-self/
 */
class Solution_238 {

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        prefix[0] = 1;
        suffix[n - 1] = 1;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = prefix[i] * suffix[i];
        }
        return result;
    }

    public int[] productExceptSelf2(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int prefix = 1;
        result[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            result[i] = result[i + 1] * nums[i + 1];
        }
        for (int i = 1; i < n; i++) {
            prefix = prefix * nums[i - 1];
            result[i] = result[i] * prefix;
        }
        return result;
    }
}
