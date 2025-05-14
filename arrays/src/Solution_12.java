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
        StringBuilder sb = new StringBuilder();
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

    class Solution2 {
        int getFirstDigit(int num) {
            while (num > 9) {
                num = num / 10;
            }
            return num;
        }

        public String intToRoman(int num) {
            if (num == 0) return "";
            int f = getFirstDigit(num);
            if (f != 4 && f != 9) {
                if (num >= 1000) {
                    return "M" + intToRoman(num - 1000);
                } else if (num >= 500) {
                    return "D" + intToRoman(num - 500);
                } else if (num >= 100) {
                    return "C" + intToRoman(num - 100);
                } else if (num >= 50) {
                    return "L" + intToRoman(num - 50);
                } else if (num >= 10) {
                    return "X" + intToRoman(num - 10);
                } else if (num >= 5) {
                    return "V" + intToRoman(num - 5);
                } else {
                    return "I" + intToRoman(num - 1);
                }
            } else {
                if (num >= 900) {
                    return "CM" + intToRoman(num - 900);
                } else if (num >= 400) {
                    return "CD" + intToRoman(num - 400);
                } else if (num >= 90) {
                    return "XC" + intToRoman(num - 90);
                } else if (num >= 40) {
                    return "XL" + intToRoman(num - 40);
                } else if (num >= 9) {
                    return "IX" + intToRoman(num - 9);
                }
                return "IV" + intToRoman(num - 4);
            }
        }
    }
}