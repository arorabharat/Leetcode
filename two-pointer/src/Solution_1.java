import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/two-sum/
 */
class Solution_1 {

    /**
     * Using hashmap,
     * we hash all the numbers and iterate through all the element if the target - current number exist in the map then we have found the solution.
     * We do not pre hash to avoid picking the same number again , we use running hash to solve that problem
     */
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        Map<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int req = target - nums[i];
            if (hash.containsKey(req)) {
                return new int[]{hash.get(req), i};
            }
            hash.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    /**
     * Two pointer,
     * Sort the array and take two pointer i and j
     */
    public int[] twoSum2(int[] nums, int target) {
        int n = nums.length;
        int i = 0;
        int j = n - 1;
        int[][] numsWithIndex = new int[n][2];
        for (int k = 0; k < n; k++) {
            numsWithIndex[k][0] = nums[k];
            numsWithIndex[k][1] = k;
        }
        Arrays.sort(numsWithIndex, Comparator.comparingInt(a -> a[0]));
        while (i < j) {
            if (numsWithIndex[i][0] + numsWithIndex[j][0] == target) {
                return new int[]{numsWithIndex[i][1], numsWithIndex[j][1]};
            } else if (numsWithIndex[i][0] + numsWithIndex[j][0] < target) {
                i++;
            } else {
                j--;
            }
        }
        return new int[]{-1, -1};
    }
}
