import javax.lang.model.element.NestingKind;
import java.util.ArrayList;
import java.util.List;

public class Solution_43 {

    private int valAt(String num, int i) {
        return i >= 0 ? num.charAt(i) - '0' : 0;
    }

    // (a1*100 + a2*10 + a3) * (b1*100 + b2*10 + b3)
    // b3*(a1*100 + a2*10 + a3) + b2*10(
    // 10


    private String add(List<String> numList) {
        int maxNumLen = 0;
        for (String num : numList) {
            maxNumLen = Math.max(maxNumLen, num.length());
        }
        int carry = 0;
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < maxNumLen; i++) {
            int digitSum = carry;
            for (String num : numList) {
                digitSum = digitSum + valAt(num, num.length() - 1 - i);
            }
            carry = digitSum / 10;
            sbr.append(digitSum % 10);
        }
        if (carry > 0) {
            sbr.append(carry);
        }
        return sbr.reverse().toString();
    }

    private String multiply(String op1, int op2, int tenPower) {
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < tenPower; i++) {
            sbr.append('0');
        }
        int carry = 0;
        for (int i = op1.length() - 1; i >= 0; i--) {
            int v = valAt(op1, i) * op2 + carry;
            carry = v / 10;
            sbr.append(v % 10);
        }
        if (carry > 0) {
            sbr.append(carry);
        }
        return sbr.reverse().toString();
    }


    public String multiply(String num1, String num2) {
        List<String> digitMultiplyResults = new ArrayList<>();
        int tenPower = 0;
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        for (int i = num2.length() - 1; i >= 0; i--) {
            digitMultiplyResults.add(multiply(num1, valAt(num2, i), tenPower));
            tenPower++;
        }
        return add(digitMultiplyResults);
    }

    static void main() {
        Solution_43 solution43 = new Solution_43();
        System.out.println(solution43.multiply("12", "12"));
    }
}
