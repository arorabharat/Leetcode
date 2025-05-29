/**
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * @see DSA#TWO_POINTER
 */
class Solution_167 {


    class Solution {
        public int[] twoSum(int[] numbers, int target) {
            int s = 0;
            int e = numbers.length - 1;
            while (s < e) {
                if (numbers[s] + numbers[e] < target) {
                    s++;
                } else if (numbers[s] + numbers[e] > target) {
                    e--;
                } else {
                    return new int[]{s + 1, e + 1};
                }
            }
            return new int[]{0, 0};
        }
    }


    class Solution2 {

        public int[] twoSum(int[] nums, int target) {
            int i = 0;
            int j = nums.length - 1;
            while (i < j) {
                int a = nums[i];
                int b = nums[j];
                if (a + b == target) {
                    return new int[]{i + 1, j + 1};
                } else if (a + b < target) {
                    i++;
                } else {
                    j--;
                }
            }
            return new int[]{-1, -1};
        }
    }

}