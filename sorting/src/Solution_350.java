import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * https://leetcode.com/problems/intersection-of-two-arrays-ii/
 */
class Solution_350 {

    public int[] intersect(int[] nums1, int[] nums2) {
        int CHARS = 1001;
        int[][] count = new int[2][CHARS];
        for (int i = 0; i < nums1.length; i++) {
            count[0][i]++;
        }
        for (int i = 0; i < nums2.length; i++) {
            count[1][i]++;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < CHARS; i++) {
            int min = Math.min(count[0][i], count[1][i]);
            for (int j = 0; j < min; j++) {
                list.add(i);
            }
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    /**
     * Approach 2
     * Using hashmap
     */
    public int[] intersect2(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect2(nums2, nums1);
        }
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int n : nums1) {
            m.put(n, m.getOrDefault(n, 0) + 1);
        }
        int k = 0;
        for (int n : nums2) {
            int cnt = m.getOrDefault(n, 0);
            if (cnt > 0) {
                nums1[k++] = n;
                m.put(n, cnt - 1);
            }
        }
        return Arrays.copyOfRange(nums1, 0, k);
    }
}