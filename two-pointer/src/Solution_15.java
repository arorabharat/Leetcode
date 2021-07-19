import java.util.*;

/**
 * https://leetcode.com/problems/3sum/
 */
class Solution_15 {

    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        Set<List<Integer>> triplets = new HashSet<>();
        for (int i = 0; i < n - 2; i++) {
            int j = i + 1;
            int k = n - 1;
            int targetSum = -nums[i];
            while (j < k) {
                if (nums[j] + nums[k] < targetSum) {
                    j++;
                } else if (nums[j] + nums[k] > targetSum) {
                    k--;
                } else {
                    triplets.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++;
                    k--;
                }
            }
        }
        return new ArrayList<>(triplets);
    }
}