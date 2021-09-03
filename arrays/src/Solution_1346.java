import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution_1346 {

    /**
     * Hashtable / hashset  approach
     */
    public boolean checkIfExist(int[] arr) {
        Set<Integer> set = new HashSet<>();
        int zero = 0;
        for (int n : arr) {
            set.add(n);
            if (n == 0) zero++;
        }
        for (int n : arr) {
            if (n != 0 && set.contains(n * 2)) {
                return true;
            }
        }
        return zero > 1;
    }

    boolean binarySearch(int[] arr, int s, int e, int key) {
        if (s <= e) {
            int m = s + (e - s) / 2;
            if (arr[m] == key) {
                return true;
            } else if (arr[m] < key) {
                return binarySearch(arr, m + 1, e, key);
            } else {
                return binarySearch(arr, s, m - 1, key);
            }
        }
        return false;
    }

    /**
     * Sorting + binary search
     */
    public boolean checkIfExist2(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] >= 0 && binarySearch(arr, i + 1, n - 1, arr[i] * 2)) {
                return true;
            } else if (arr[i] < 0 && binarySearch(arr, 0, i - 1, arr[i] * 2)) {
                return true;
            }
        }
        return false;
    }
}

