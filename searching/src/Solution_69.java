/**
 * https://leetcode.com/problems/sqrtx/
 */
class Solution_69 {

    int binarySearch(int s, int e, int x) {
        if (s < e) {
            int m = s + (e - s) / 2 + 1;
            long p = (long) m * m;
            if (p == x) {
                return m;
            } else if (p < x) {
                return binarySearch(m, e, x);
            } else {
                return binarySearch(s, m - 1, x);
            }
        }
        return s;
    }

    public int mySqrt(int x) {
        return (int) binarySearch(0, x, x);
    }

    /**
     * Iterative approach
     * @see DSA#BINARY_SEARCH
     */
    public int mySqrt2(int x) {
        if (x < 2) return x;
        long p;
        int m, s = 2, e = x / 2;
        while (s <= e) {
            m = s + (e - s) / 2;
            p = (long)m * m;
            if (p > x) e = m - 1;
            else if (p < x) s = m + 1;
            else return m;
        }
        return e;
    }
}
