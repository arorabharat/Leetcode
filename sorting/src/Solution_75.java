import java.util.Arrays;

// Arrays method do not return the object, the modify the original array
class Solution_75 {

    class Solution {

        public void sortColors(int[] nums) {
            int n = nums.length;
            int nextZeroIndex = 0;
            for (int currIndex = 0; currIndex < n; currIndex++) {
                if (nums[currIndex] == 0) {
                    swap(nums, currIndex, nextZeroIndex);
                    nextZeroIndex++;
                }
            }
            int nextOneIndex = nextZeroIndex;
            for (int currIndex = nextZeroIndex; currIndex < n; currIndex++) {
                if (nums[currIndex] == 1) {
                    swap(nums, currIndex, nextOneIndex);
                    nextOneIndex++;
                }
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }


    public void sortColors(int[] nums) {
        Arrays.sort(nums);
    }

    void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // [2,0,2,1,1,0]
    public void sortColors1(int[] nums) {
        int first = -1;
        int last = nums.length;
        for (int i = 0; i < nums.length; ) {
            if (nums[i] == 0 && first < i) {
                first++;
                swap(first, i, nums);
            } else if (nums[i] == 2 && i < last) {
                last--;
                swap(last, i, nums);
            } else {
                i++;
            }
        }
    }
}