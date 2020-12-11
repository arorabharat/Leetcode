class Solution_7 {
    public int reverse(int x) {
        if (x == Integer.MIN_VALUE) {
            return 0;
        }
        boolean isNegative = x < 0;
        StringBuilder sb = new StringBuilder(Integer.toString(Math.abs(x)));
        String rev = sb.reverse().toString();
        long r = Long.parseLong(rev);
        if (isNegative) {
            if (-1 * r < Integer.MIN_VALUE) {
                return 0;
            } else {
                return -1 * (int) r;
            }
        } else {
            if (r > Integer.MAX_VALUE) {
                return 0;
            } else {
                return (int) r;
            }
        }
    }
}