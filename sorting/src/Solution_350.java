import java.util.ArrayList;
import java.util.List;

public class Solution_350 {

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
}