import java.util.Arrays;

class Solution_16 {
    /**
     * [-4,-1,1,2]
     * target : 2
     * We use three pointers : i, j and k in the sorted array. Where i<j<k
     * We fix the i and iterate using the j and k in the following fashion.
     * target - (sum) = diff
     * so if diff is positive we need to decrease the diff by decreasing a number in sum which would be pointer at k, as the array is sorted so nums[k-1] < nums[k]
     * so if diff is negative we need to increase the diff by increasing a number in sum which would be pointer at j, as the array is sorted so nums[j] > nums[j+1]
     *
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        int closest = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < n - 2; i++) {
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                int diff = target - sum;
                if (Math.abs(target - closest) > Math.abs(diff)) {
                    closest = sum;
                }
                if (diff > 0) {
                    j++;
                } else if (diff < 0) {
                    k--;
                } else {
                    return target;
                }
            }
        }
        return closest;
    }
}