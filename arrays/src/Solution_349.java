import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/intersection-of-two-arrays/
 */
class Solution_349 {

    public int[] intersection(int[] nums1, int[] nums2) {
        boolean[] hash = new boolean[1001];
        for (int n : nums1) {
            hash[n] = true;
        }
        Set<Integer> answer = new HashSet<>();
        for (int n : nums2) {
            if (hash[n]) {
                answer.add(n);
            }
        }
        int m = answer.size();
        int[] arr = new int[m];
        int i = 0;
        for (int n : answer) {
            arr[i] = n;
            i++;
        }
        return arr;
    }
}
