class Solution_930 {

    // TODO optimisation pending
    /**
     * O(N^2)
     */
    public int numSubarraysWithSum(int[] nums, int goal) {
        int count = 0;
        int n = nums.length;
        for (int k = 0; k < n; k++) {
            int sum = nums[k];
            count = (sum == goal) ? count + 1 : count;
            for (int l = k + 1; l < n; l++) {
                if (sum + nums[l] == goal) {
                    count++;
                    sum = sum + nums[l];
                } else if (sum + nums[l] < goal) {
                    sum = sum + nums[l];
                } else {
                    break;
                }
            }
        }
        return count;
    }
}