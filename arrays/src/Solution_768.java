import java.util.Arrays;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/max-chunks-to-make-sorted-ii/
 * TODO : there is simple solution, try that next time
 */
class Solution_768 {

    TreeMap<Integer, Integer> elementCount = new TreeMap<>();
    TreeMap<Integer, Integer> elementIndex = new TreeMap<>();

    private void plusOneElementCount(int x) {
        elementCount.put(x, elementCount.getOrDefault(x, 0) + 1);
    }

    private void incrementIndex(int x) {
        elementCount.put(x, elementCount.getOrDefault(x, 0) + 1);
    }

    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        Arrays.stream(arr).forEach(this::plusOneElementCount);
        int prev = 0;
        int next;
        for (Integer x : elementCount.navigableKeySet()) {
            next = elementCount.get(x);
            elementIndex.put(x, prev);
            prev = prev + next;
        }
        int partitionEnd = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            partitionEnd = Math.max(partitionEnd, elementIndex.get(arr[i]));
            plusOneElementCount(arr[i]);
            if (i == partitionEnd) {
                count++;
            }
        }
        return count;
    }
}
