import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    public int findMaxLength2(int[] nums) {
        int N = nums.length;
        int[] prefixSumFirstIElement = new int[N + 1];
        // prefixSumFirstIElement[i] = sum of array of element less than ith index
        // or we can say prefixSumFirstIElement i store sum of first i element.
        prefixSumFirstIElement[0] = 0;
        for (int i = 1; i <= N; i++) {
            prefixSumFirstIElement[i] = prefixSumFirstIElement[i - 1] + (nums[i - 1] == 0 ? -1 : 1);
        }
        Map<Integer, LinkedList<Integer>> prefixSumToIndex = new HashMap<>();
        for (int i = 0; i < N + 1; i++) {
            LinkedList<Integer> prefixSumIndexes = prefixSumToIndex.getOrDefault(prefixSumFirstIElement[i], new LinkedList<>());
            prefixSumIndexes.add(i);
            prefixSumToIndex.put(prefixSumFirstIElement[i], prefixSumIndexes);
        }
        int maxLen = 0;
        for (Integer prefixSum : prefixSumToIndex.keySet()) {
            Integer front = prefixSumToIndex.get(prefixSum).getFirst();
            Integer back = prefixSumToIndex.get(prefixSum).getLast();
            maxLen = Math.max(maxLen, back - front);
        }
        return maxLen;
    }

    public int findMaxLength3(int[] nums) {
        // Map from running sum to its first occurrence index
        Map<Integer, Integer> firstIndex = new HashMap<>();
        // Initialize: sum = 0 “occurs” just before the array starts
        firstIndex.put(0, -1);

        int maxLen = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            // Update running sum: +1 for a 1, -1 for a 0
            sum += (nums[i] == 1 ? 1 : -1);

            if (firstIndex.containsKey(sum)) {
                // We’ve seen this sum before; subarray (firstIndex.get(sum)+1 .. i)
                // has net sum zero → equal #0s and #1s
                int prevIndex = firstIndex.get(sum);
                int len = i - prevIndex;
                if (len > maxLen) {
                    maxLen = len;
                }
            } else {
                // First time seeing this sum: record index
                firstIndex.put(sum, i);
            }
        }

        return maxLen;
    }
}
