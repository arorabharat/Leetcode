/**
 * https://leetcode.com/problems/product-of-array-except-self/
 */
class Solution_238 {

    class Approach_1 {

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

    }

    class Approach_2 {

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

    class Approach_3 {

        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            int output[] = new int[n];
            if (n == 0) {
                return output;
            }
            if (n == 1) {
                return nums;
            }
            int p = 1;
            int s = 1;
            int countZero = 0;
            int zeroIndex = -1;
            for (int i = 0; i < n; i++) {
                if (nums[i] == 0) {
                    zeroIndex = i;
                    countZero++;
                } else {
                    s = s * nums[i];
                }
                if (countZero > 1) {
                    return output;
                }
            }
            if (countZero == 1) {
                output[zeroIndex] = s;
                return output;
            }
            for (int i = 0; i < n; i++) {
                s = s / nums[i];
                output[i] = s * p;
                p = p * nums[i];
            }
            return output;
        }
    }
}
