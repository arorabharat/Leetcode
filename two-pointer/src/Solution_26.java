// TODO: 01/05/25 - Do it again in a better way
public class Solution_26 {

    public int removeDuplicates(int[] nums) {
        int length = nums.length;
        if (length < 2) return length;
        int curr = 0;
        int nextUniqueIndex = 0;
        int uniqueCount = 0;
        while (nextUniqueIndex < length) {
            uniqueCount++;
            nums[curr] = nums[nextUniqueIndex];
            while (nextUniqueIndex < length && nums[curr] == nums[nextUniqueIndex]) {
                nextUniqueIndex++;
            }
            curr++;
        }
        return uniqueCount;
    }
}
