/**
 * https://leetcode.com/problems/first-missing-positive/
 */
class Solution_41 {
    int n;

    boolean range(int i) {
        return 1 <= i && i <= n;
    }

    void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    boolean present(int[] nums, int i, int v) {
        return nums[i] == v;
    }

    public int firstMissingPositive(int[] nums) {

        this.n = nums.length;

        for (int i = 0; i < n; i++) {
            while (range(nums[i]) && !present(nums, nums[i] - 1, nums[i])) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!present(nums, i, i + 1)) {
                return i + 1;
            }
        }
        return n + 1;
    }
}

