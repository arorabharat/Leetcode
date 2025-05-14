public class Solution_525 {


    public int findMaxLength(int[] nums) {
        int N = nums.length;
        int[] prefix = new int[N + 1];
        // prefix[i] = sum of array of element less than ith index
        // or we can say prefix i store sum of first i element.
        prefix[0] = 0;
        for (int i = 1; i <= N; i++) {
            prefix[i] = prefix[i - 1] + (nums[i - 1] == 0 ? -1 : 1);
        }
        int maxLen = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j <= N; j++) {
                // [i,j)
                if (prefix[j] - prefix[i] == 0) {
                    maxLen = Math.max(maxLen, j - i);
                }
            }
        }
        return maxLen;
    }
}
