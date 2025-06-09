// TODO: 09/06/25 - very confusing question
public class Solution_169 {
    class Solution {
        public int majorityElement(int[] nums) {
            int count = 1;
            int candidate = nums[0];
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] == candidate) {
                    count++;
                } else {
                    count--;
                }
                if (count == 0) {
                    candidate = nums[i];
                    count = 1;
                }
            }
            return candidate;
        }
    }
}
