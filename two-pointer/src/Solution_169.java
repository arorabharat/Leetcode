/**
 *
 */
class Solution_169 {
    /**
     * TODO incorrect solution, do it again
     */
    public int majorityElement(int[] nums) {
        int n = nums.length;
        if (n == 0) return nums.length;
        int bucket1 = 0, count1 = 0, bucket2 = 0, count2 = 0;
        for (int num : nums) {
            if (count1 == count2) {
                count1 = 0;
                count2 = 0;
            }
            if (count1 == 0 || bucket1 == num) {
                bucket1 = num;
                count1++;
            } else if (count2 == 0 || bucket2 == num) {
                bucket2 = num;
                count2++;
            } else {
                if (count1 < count2) {
                    bucket1 = num;
                    count1 = 1;
                } else {
                    bucket2 = num;
                    count2 = 1;
                }
            }
        }
        if (count1 < count2) return bucket2;
        if (count2 < count1) return bucket1;
        return Integer.MIN_VALUE;
    }
}
