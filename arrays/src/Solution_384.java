import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * https://leetcode.com/problems/shuffle-an-array/
 * Link to algo : https://www.youtube.com/watch?v=4zx5bM2OcvA
 */
class Solution_384 {

    /**
     * approach 1 : Pseudo random number picking approach
     * Time Complexity :  O( N^2 )
     * Space Complexity :  O( N )
     */
    static class Solution {
        private final Random rand = new Random();
        private int[] array;
        private int[] original;

        public Solution(int[] nums) {
            array = nums;
            original = nums.clone();
        }

        private List<Integer> getArrayCopy() {
            List<Integer> asList = new ArrayList<>();
            for (int j : array) {
                asList.add(j);
            }
            return asList;
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
     * Time Complexity :  O( N )
     * Space Complexity :  O( constant )
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

