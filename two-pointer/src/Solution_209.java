import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-size-subarray-sum/
 */
class Solution_209 {

    class Approach_1 {
        /**
         * We maintain two pointer
         * ith element point to first element
         * jth pointer points to the next element that can be include in the window
         * if the sum is greater than or equal to the target we take len
         * otherwise we keep on incrementing the jth pointer
         */
        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length;
            if (n == 0) return 0;
            int i = 0;
            int j = 1;
            int sum = nums[0];
            int minArrayLen = Integer.MAX_VALUE;
            while (j < n) {
                if (sum < target) {
                    sum = sum + nums[j];
                    j++;
                } else {
                    minArrayLen = Math.min(j - i, minArrayLen);
                    sum = sum - nums[i];
                    i++;
                }
            }
            while (sum >= target) {
                minArrayLen = Math.min(j - i, minArrayLen);
                sum = sum - nums[i];
                i++;
            }
            return minArrayLen == Integer.MAX_VALUE ? 0 : minArrayLen;
        }
    }

    // using two pointers approach
    class Approach_2 {
        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length;
            if (n == 0 || target <= 0) return 0;
            int s = -1;
            int sum = 0;
            int minLen = Integer.MAX_VALUE;
            for (int e = 0; e < n; e++) {
                sum = sum + nums[e];
                while (sum >= target) {
                    minLen = Math.min(minLen, (e - s));
                    s++;
                    sum = sum - nums[s];
                }
            }
            return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
        }
    }

    class Approach_3 {

        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length;
            if (n == 0 || target <= 0) return 0;
            int[] prefixSum = new int[n + 1];
            int minLen = Integer.MAX_VALUE;
            prefixSum[0] = 0;
            for (int e = 0; e < n; e++) {
                prefixSum[e + 1] = prefixSum[e] + nums[e];
            }
            for (int s = 1; s <= n; s++) {
                // see binary search return type definition
                int ge = Arrays.binarySearch(prefixSum, s, n + 1, (target + prefixSum[s - 1]));
                if (ge < 0) {
                    ge = -1 * ge - 1;
                }
                if (ge != n + 1 && minLen > (ge - s + 1)) {
                    minLen = (ge - s + 1);
                }
            }
            return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
        }
    }
}