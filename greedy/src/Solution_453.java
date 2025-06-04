public class Solution_453 {
    class Solution {

        public int minMoves(int[] nums) {
            int sum = 0;
            for (int x : nums) {
                sum = sum + x;
            }
            int min = Integer.MAX_VALUE;
            for (int x : nums) {
                min = Math.min(x, min);
            }
            return sum - min * nums.length;
        }
    }
}
