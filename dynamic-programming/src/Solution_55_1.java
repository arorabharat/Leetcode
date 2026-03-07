/**
 * https://leetcode.com/problems/jump-game/
 */
class Solution_55_1 {

    public boolean canJump(int[] nums) {
        int N = nums.length;
        int maxReachableIndex = 0;
        for (int i = 0; i < N; i++) {
            if (i > maxReachableIndex) return false;
            int maxReachableFromCurrentIndex = i + nums[i];
            maxReachableIndex = Math.max(maxReachableIndex, maxReachableFromCurrentIndex);
        }
        return maxReachableIndex >= N - 1;
    }
}