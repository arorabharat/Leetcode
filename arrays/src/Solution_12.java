/**
 * https://leetcode.com/problems/integer-to-roman/
 */
class Solution_12 {

    int[] values = {1, 4, 5, 9,
            10, 40, 50, 90,
            100, 400, 500, 900,
            1000};
    String[] romans = {"I", "IV", "V", "IX",
            "X", "XL", "L", "XC",
            "C", "CD", "D", "CM",
            "M"};

    private void appendN(StringBuilder sb, String str, int freq) {
        sb.append(String.valueOf(str).repeat(Math.max(0, freq)));
    }

    public String intToRoman(int num) {
        if (num < 1) return null;
        int n = values.length;
        StringBuilder sb = new StringBuilder("");
        for (int i = n - 1; i >= 0; i--) {
            if (num == 0) break;
            if (values[i] <= num) {
                int count = num / values[i];
                num = num % values[i];
                appendN(sb, romans[i], count);
            }
        }
        return sb.toString();
    }
}