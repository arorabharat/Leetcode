import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/max-chunks-to-make-sorted/
 */
class Solution_769 {

    private int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    /**
     * Approach 1
     * Time Complexity :  O( N )
     * Space Complexity :  O( N )
     */
    public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        int[] index = new int[n];
        IntStream.range(0, n).forEach(i -> index[arr[i]] = i);
        int p = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            p = max(p, index[arr[i]], index[i]);
            if (i == p) {
                count++;
            }
        }
        return count;
    }


    /**
     * Approach 2
     * Time Complexity :  O( N )
     * Space Complexity :  O( 1 )
     * Basically we do not need the index because if we have encountered an element which is greater than index
     * that means we can not sort that element unless the element and its correct index belong to same partition.
     */
    public int maxChunksToSorted2(int[] arr) {
        int n = arr.length;
        int partitionEnd = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            partitionEnd = Math.max(partitionEnd, arr[i]);
            if (i == partitionEnd) {
                count++;
            }
        }
        return count;
    }
}
