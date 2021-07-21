import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * https://leetcode.com/problems/shuffle-an-array/
 */
class Solution_384 {

    /**
     * approach 1 : Psuedo random number picking approach
     * Time complexity : O(N^2)
     */
    static class Solution {
        private int[] array;
        private int[] original;
        private final Random rand = new Random();

        private List<Integer> getArrayCopy() {
            List<Integer> asList = new ArrayList<>();
            for (int j : array) {
                asList.add(j);
            }
            return asList;
        }

        public Solution(int[] nums) {
            array = nums;
            original = nums.clone();
        }

        public int[] reset() {
            array = original;
            original = original.clone();
            return array;
        }

        public int[] shuffle() {
            List<Integer> arrayList = getArrayCopy();
            for (int i = 0; i < array.length; i++) {
                int pickedNumber = rand.nextInt(arrayList.size());
                array[i] = arrayList.get(pickedNumber);
                arrayList.remove(pickedNumber);
            }
            return array;
        }
    }

    /**
     * Approach 2
     *
     * @see DSA#FISHER_YATES_ALGORITHM
     * Time complexity : O(N)
     */
    static class Solution2 {
        int[] nums;
        int[] original;
        int n;

        public Solution2(int[] nums) {
            this.nums = nums;
            this.n = nums.length;
            this.original = new int[n];
            System.arraycopy(nums, 0, original, 0, n);
        }

        /**
         * Resets the array to its original configuration and return it.
         */
        public int[] reset() {
            System.arraycopy(original, 0, nums, 0, n);
            return this.nums;
        }

        /**
         * Returns a random shuffling of the array.
         */
        public int[] shuffle() {
            Random random = new Random();
            for (int i = 1; i <= n; i++) {
                int j = random.nextInt(i);
                swap(j, i - 1);
            }
            return this.nums;
        }

        private void swap(int i, int j) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }
    }

}

