class Solution_67 {

    public String addBinary(String a, String b) {
        StringBuilder sa = new StringBuilder(a);
        StringBuilder sb = new StringBuilder(b);
        String ar = sa.reverse().toString();
        String br = sb.reverse().toString();
        int m = ar.length();
        int n = br.length();
        int carry = 0;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < Math.max(m, n); i++) {
            int ai = 0;
            int bi = 0;
            if (i < m) {
                ai = ar.charAt(i) - '0';
            }
            if (i < n) {
                bi = br.charAt(i) - '0';
            }
            int s = ai + bi + carry;
            carry = s / 2;
            s = s % 2;
            res.append(s);
        }
        if (carry != 0) {
            res.append(carry);
        }
        return res.reverse().toString();
    }
}