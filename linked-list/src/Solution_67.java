/**
 * https://leetcode.com/problems/add-binary/
 */
class Solution_67 {

    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        while (i >= 0 && j >= 0) {
            int s = a.charAt(i) - '0' + b.charAt(j) - '0' + carry;
            carry = s / 2;
            sb.append(s % 2);
            i--;
            j--;
        }
        while (i >= 0) {
            int s = a.charAt(i) - '0' + carry;
            carry = s / 2;
            sb.append(s % 2);
            i--;
        }
        while (j >= 0) {
            int s = b.charAt(j) - '0' + carry;
            carry = s / 2;
            sb.append(s % 2);
            j--;
        }
        if (carry != 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }


    /**
     * Classic argument swapping approach
     *
     * @see Level#GOOD
     */
    public String addBinary2(String a, String b) {
        int m = a.length();
        int n = b.length();
        if (m < n) {
            return addBinary2(b, a);
        }
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        int j = n - 1;
        for (int i = m - 1; i >= 0; --i) {
            if (a.charAt(i) == '1'){
                ++carry;
            }
            if (j >= 0 && b.charAt(j--) == '1'){
                ++carry;
            }
            if (carry % 2 == 1){
                sb.append('1');
            }
            else{
                sb.append('0');
            }
            carry /= 2;
        }
        if (carry == 1) {
            sb.append('1');
        }
        sb.reverse();
        return sb.toString();
    }
}