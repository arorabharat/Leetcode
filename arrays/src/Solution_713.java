public class Solution_713 {

    class Approach_1 {
        public int numSubarrayProductLessThanK(int[] nums, int k) {
            int product = 1;
            int length = nums.length;
            int left = 0;
            int right = 1;
            int count = 0;
            while (right < length && left < length) {
                if (product * nums[right] < k) {
                    product = product * nums[right];
                    count++;
                    right++;
                } else {
                    product = product / nums[left];
                    left++;
                }
            }
            return count;
        }
    }
}
