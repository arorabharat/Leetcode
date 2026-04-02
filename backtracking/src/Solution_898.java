import java.util.HashSet;
import java.util.Set;

public class Solution_898 {

    Set<Integer> bitwise(int[] arr, int s, int e) {
        if (s > e) {
            throw new RuntimeException("Invalid range");
        }
        if (s == e) {
            return new HashSet<>(arr[s]);
        }
        Set<Integer> subsetBitwise = bitwise(arr, s + 1, e);
        Set<Integer> setBitwise = new HashSet<>(subsetBitwise);
        for (int uv : subsetBitwise) {
            int nuv = uv * arr[s];
            setBitwise.add(nuv);
        }
        return setBitwise;
    }

    public int subarrayBitwiseORs(int[] arr) {
        return bitwise(arr, 0, arr.length - 1).size();
    }
}
