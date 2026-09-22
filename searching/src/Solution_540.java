public class Solution_540 {

    class Solution {
        int binarySearch(int[] nums) {
            int s = 0;
            int e = nums.length - 1;
            while (s < e) {
                int m = s + (e - s) / 2;
                if (m % 2 == 0) {
                    if (nums[m] == nums[m + 1]) {
                        s = m + 1;
                    } else {
                        e = m;
                    }
                } else {
                    if (nums[m] == nums[m - 1]) {
                        s = m + 1;
                    } else {
                        e = m - 1;
                    }
                }
            }
            return nums[s];
        }

        public int singleNonDuplicate(int[] nums) {
            return binarySearch(nums);
        }
    }
}
