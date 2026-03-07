import java.util.Arrays;

/**
 * https://leetcode.com/problems/jump-game-ii/
 */
class Solution_45_1 {

    public int jump(int[] nums) {
        int N = nums.length;
        int[] minJumps = new int[N];
        Arrays.fill(minJumps, Integer.MAX_VALUE);
        minJumps[0] = 0;
        for (int i = 1; i < N; i++) {
            for (int j = i; j < i + nums[i - 1] && j < N; j++) {
                minJumps[j] = Math.min(minJumps[j], 1 + minJumps[i - 1]);
            }
        }
        return minJumps[N - 1];
    }
}
