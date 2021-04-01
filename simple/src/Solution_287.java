class Solution_287 {

    private void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // cyclic array iteration
    public int findDuplicate(int[] nums) {
        int i = 0;
        while (nums[i] != i) {
            if (nums[nums[i]] == nums[i]) {
                return nums[i];
            }
            swap(i, nums[i], nums);
        }
        return nums[0];
    }
}
