import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/arithmetic-slices/
 */
class Solution_413 {

    /**
     * count length of the maximum size Arithmetic progression that can be made from given index.
     * nums = [1,2,3,4,5,8,11]
     * ap =   [5,4,3,2,3,2,1]
     * because the array need to continuous , so starting from given number iterate over all the next number, the number would be either included in AP or not.
     * If number is not included then it mark the end of the AP.
     * In this way calculate the length of the AP foe each index.
     * So given a AP of  size 5 like 1,2,3,4,5 we can make ap[i] - 2 sub arrays of length greater than 3. AP size should be equal to and greater than 3
     * Do the sum for all the number to get all possible sub arrays
     * Time Complextiy : O(N^2)
     */
    public int numberOfArithmeticSlices(int[] nums) {
        int n = nums.length;
        int[] ap = new int[n];
        int d = Integer.MIN_VALUE;
        boolean init;
        for (int i = 0; i < n; i++) {
            int count = 1;
            init = false;
            for (int j = i + 1; j < n; j++) {
                if (!init) {
                    d = nums[j] - nums[j - 1];
                    init = true;
                    count++;
                } else if (nums[j] - nums[j - 1] == d) {
                    count++;
                } else {
                    break;
                }
            }
            ap[i] = count;
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (ap[i] >= 3) {
                sum = sum + (ap[i] - 2);
            }
        }
        return sum;
    }

    /**
     * Optimised.
     * we could iterate from the right to left, keep counting the apLength and assign it every index.
     * If the AP breaks then we re initialised the AP length to 2, continue the AP counting again.
     * Time Complexity : O(1)
     */
    public int numberOfArithmeticSlices2(int[] nums) {
        int n = nums.length;
        if (n < 3) return 0;
        int[] ap = new int[n];
        int difference = nums[n - 2] - nums[n - 1];
        int apLength = 2;
        ap[n - 1] = 1;
        ap[n - 2] = 2;
        for (int i = n - 3; i >= 0; i--) {
            if (difference == nums[i] - nums[i + 1]) {
                apLength++;
            } else {
                apLength = 2;
                difference = nums[i] - nums[i + 1];
            }
            ap[i] = apLength;
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + Math.max(0, ap[i] - 2);
        }
        return sum;
    }

    /**
     * Sliding window style
     * index :     [0,1,2,3,4]
     * arr :       [1,2,3,4,5]
     * diff index: [  0,1,2,3]
     * diff value: [  1,1,1,1]
     * if diff at index 1 match diff at index 0, that means value at index 2 in arr is in AP which is starting at 0;
     */
    public int numberOfArithmeticSlices3(int[] nums) {
        int n = nums.length;
        if (n < 3) return 0;
        List<Integer> diff = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            diff.add(nums[i] - nums[i - 1]);
        }
        int sum = 0, startIndex = 0;
        for (int j = 1; j < diff.size(); j++) {
            int endIndex = j + 1;
            if (diff.get(j - 1).equals(diff.get(j))) {
                int apLength = endIndex + 1 - startIndex; // +1 because end index is inclusive
                sum = sum + (apLength - 2);
            } else
                startIndex = j;
        }

        return sum;
    }
}