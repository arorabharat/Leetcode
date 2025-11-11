// TODO - unable to do
public class Solution_713 {


    class Approach_1 {
        public int numSubarrayProductLessThanK(int[] nums, int k) {
            int length = nums.length;
            int count = 0;
            for (int i = 0; i < length; i++) {
                int product = 1;
                for (int j = i; j < length; j++) {
                    product = product * nums[j];
                    if (product < k) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
            return count;
        }
    }

    class Approach_2 {

        // [10,5,2,6]
        // [10,10] k=8
        public int numSubarrayProductLessThanK(int[] nums, int k) {
            int product = 1;
            int length = nums.length;
            int left = 0;
            int right = 1;
            int count = 0;
            while (right < length && left < length) {
                product = product * nums[right];
                if (product < k) {
                    right++;
                } else {
                    count = count + right - left;
                    product = product / nums[left];
                    left++;
                }
            }
            return count;
        }
    }
}
