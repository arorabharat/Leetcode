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

    public class Solution1 {
        private static boolean inRange(int n, int nums) {
            return nums > 0 && nums <= n;
        }

        /**
         * Finds the smallest missing positive integer in the array in O(N) time
         * and O(1) extra space by using the array itself for indexing.
         *
         * @param nums The input array of integers.
         * @return The first missing positive integer.
         */
        public int firstMissingPositive(int[] nums) {
            int n = nums.length;

            // 1. Place each number v in the range [1..n] into index v-1 by swapping.
            for (int i = 0; i < n; i++) {
                // Keep swapping until nums[i] is either out of range,
                // already in the correct spot, or a duplicate.
                // v is not already at nums[v-1]
                while (inRange(n, nums[i]) && nums[nums[i] - 1] != nums[i]) {
                    swap(nums, i, nums[i] - 1);
                }
            }

            // 2. Scan the array: the first index i where nums[i] != i+1 is our answer.
            for (int i = 0; i < n; i++) {
                if (nums[i] != i + 1) {
                    return i + 1;
                }
            }

            // 3. If every slot 1..n is filled correctly, the answer is n+1.
            return n + 1;
        }

        private void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }

        // Example tests
        public void main(String[] args) {
            Solution1 sol = new Solution1();
            System.out.println(sol.firstMissingPositive(new int[]{1, 2, 0}));       // 3
            System.out.println(sol.firstMissingPositive(new int[]{3, 4, -1, 1}));   // 2
            System.out.println(sol.firstMissingPositive(new int[]{7, 8, 9, 11, 12})); // 1
        }
    }

}

