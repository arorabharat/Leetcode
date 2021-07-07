import java.util.Arrays;

// TODO
public class Solution_1509 {

    /**
     * 0 1 2 3 4
     * X X X X X
     * we will sort the array.
     *
     * 0 1 2 3 4 5
     * Y Y Y X X X
     *
     * 0 1 2 3 4 5
     * X X X Y Y Y
     *
     * 0 1 2 3 4 5
     * X X X Y Y Y
     *
     */
    public int minDifference(int[] nums) {
        int n = nums.length;
        if (n <= 3) return 0;
        Arrays.sort(nums);
        return Math.min(
                Math.min(nums[n - 1] - nums[3], nums[n - 4] - nums[0]),
                Math.min(nums[n - 2] - nums[2], nums[n - 3] - nums[1])
        );
    }
}