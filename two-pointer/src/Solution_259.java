import java.util.Arrays;

/**
 * https://leetcode.com/problems/3sum-smaller/
 */
class Solution_259 {

    /**
     * brute force approach
     * Time complexity : O(n^3)
     */
    public int threeSumSmaller(int[] nums, int target) {
        int n = nums.length;
        if (n < 3) return 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] < target) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * @see DSA#BINARY_SEARCH
     */
    private int lowerBound(int[] nums, int s, int e, int key) {
        if (s < e) {
            int m = s + (e + 1 - s) / 2; // right mid
            if (nums[m] >= key) {
                return lowerBound(nums, s, m - 1, key);
            } else {
                return lowerBound(nums, m, e, key);
            }
        }
        return s;
    }

    /**
     * Using binary search
     * Instead of counting each pair, just find the max kth index possible for a given j
     * You could take all the elements from j+1 index to the kth index
     * Time complexity : O(n^3)
     */
    public int threeSumSmaller2(int[] nums, int target) {
        int n = nums.length;
        if (n < 3) return 0;
        int count = 0;
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int numK = target - nums[i] - nums[j];
                int kIndex = lowerBound(nums, j, n - 1, numK);
                count = count + kIndex - j;
            }
        }
        return count;
    }

    /**
     * Modularizing the binary search approach.
     */
    public int threeSumSmaller3(int[] nums, int target) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            sum += twoSumSmaller(nums, i + 1, target - nums[i]);
        }
        return sum;
    }

    private int twoSumSmaller(int[] nums, int startIndex, int target) {
        int sum = 0;
        for (int i = startIndex; i < nums.length - 1; i++) {
            int j = lowerBound(nums, i, nums.length - 1, target - nums[i]);
            sum += j - i;
        }
        return sum;
    }

    /**
     * Approach : without binary search
     */
    public int threeSumSmaller4(int[] nums, int target) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            sum += twoSumSmallerTwoPointer(nums, i + 1, target - nums[i]);
        }
        return sum;
    }

    private int twoSumSmallerTwoPointer(int[] nums, int startIndex, int target) {
        int sum = 0;
        int i = startIndex;
        int j = nums.length - 1;
        while (i < j) {
            if (nums[i] + nums[j] >= target) {
                j--;
            } else {
                sum += j - i;
                i++;
            }
        }
        return sum;
    }
}

