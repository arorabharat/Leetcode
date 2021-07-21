import java.util.*;

/**
 * https://leetcode.com/problems/3sum/
 * TODO : read this -> https://en.wikipedia.org/wiki/3SUM
 */
class Solution_15 {

    /**
     *
     *
     *
     */

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

    /**
     * Approach 2 : without set
     */
    public List<List<Integer>> threeSum2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length && nums[i] <= 0; ++i)
            if (i == 0 || nums[i - 1] != nums[i]) {
                twoSumII(nums, i, res);
            }
        return res;
    }

    void twoSumII(int[] nums, int i, List<List<Integer>> res) {
        int lo = i + 1, hi = nums.length - 1;
        while (lo < hi) {
            int sum = nums[i] + nums[lo] + nums[hi];
            if (sum < 0) {
                ++lo;
            } else if (sum > 0) {
                --hi;
            } else {
                res.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                lo++;
                hi--;
                while (lo < hi && nums[lo] == nums[lo - 1])
                    ++lo;
            }
        }
    }

    /**
     * Approach 3 : using hashset
     */
    public List<List<Integer>> threeSum3(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length && nums[i] <= 0; ++i)
            if (i == 0 || nums[i - 1] != nums[i]) {
                twoSum(nums, i, res);
            }
        return res;
    }

    void twoSum(int[] nums, int i, List<List<Integer>> res) {
        Set<Integer> seen = new HashSet<>();
        for (int j = i + 1; j < nums.length; ++j) {
            int complement = -nums[i] - nums[j];
            if (seen.contains(complement)) {
                res.add(Arrays.asList(nums[i], nums[j], complement));
                while (j + 1 < nums.length && nums[j] == nums[j + 1]) {
                    ++j;
                }
            }
            seen.add(nums[j]);
        }
    }

    /**
     * Approach 3 : using hashset˳
     *
     * @see Level#GOOD
     * Java set internally compare the complete list
     */
    public List<List<Integer>> threeSum4(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Set<List<Integer>> unique = new HashSet<>();
        for (int i = 0; i < nums.length; ++i) {
            Set<Integer> seen = new HashSet<>();
            for (int j = i + 1; j < nums.length; ++j) {
                int complement = -nums[i] - nums[j];
                if (seen.contains(complement)) {
                    List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                    Collections.sort(triplet);
                    if (!unique.contains(triplet)) {
                        res.add(triplet);
                        unique.add(triplet);
                    }
                }
                seen.add(nums[j]);
            }
        }
        return res;
    }
}