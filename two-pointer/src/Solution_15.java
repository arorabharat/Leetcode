import java.util.*;

/**
 * https://leetcode.com/problems/3sum/
 * TODO : read this -> https://en.wikipedia.org/wiki/3SUM
 * // TODO:  -
 * // TODO: 16/04/25 - revisit the question as you are not able to solve it
 */
class Solution_15 {

    /**
     * leveraging brute force solution
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum5(int[] nums) {
        int n = nums.length;
        List<List<Integer>> tripletsList = new ArrayList<>();
        Set<Triplet> tripletsSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    Triplet newTriplet = new Triplet(nums[i], nums[j], nums[k]);
                    if (nums[i] + nums[j] + nums[k] == 0 && !tripletsSet.contains(newTriplet)) {
                        tripletsList.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        tripletsSet.add(newTriplet);
                    }
                }
            }
        }
        return tripletsList;
    }

    static class Triplet {

        private final List<Integer> numbers;

        Triplet(int a, int b, int c) {
            this.numbers = Arrays.asList(a, b, c);
            Collections.sort(numbers);
        }

        public List<Integer> getNumbers() {
            return Collections.unmodifiableList(this.numbers);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.numbers);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Triplet t2 = (Triplet) obj;
            return Arrays.equals(this.getNumbers().toArray(), t2.getNumbers().toArray());
        }
    }

    /**
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