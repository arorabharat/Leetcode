import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode.com/problems/add-to-array-form-of-integer/
 */
class Solution_989 {

    public List<Integer> addToArrayForm(int[] num, int k) {
        int n = num.length;
        int carry = 0;
        List<Integer> result = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            int a = num[i];
            int b = k % 10;
            k = k / 10;
            int sum = a + b + carry;
            carry = sum / 10;
            result.add(sum % 10);
        }
        while (k > 0) {
            int b = k % 10;
            k = k / 10;
            int sum = b + carry;
            carry = sum / 10;
            result.add(sum % 10);
        }
        if (carry > 0) {
            result.add(carry);
        }
        Collections.reverse(result);
        return result;
    }
}