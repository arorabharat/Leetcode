/**
 * https://leetcode.com/problems/maximum-69-number/
 */
class Solution_1323 {

    public int maximum69Number(int num) {
        int res = 0;
        int p = 0;
        int offset = 0;
        while (num > 0) {
            int d = num % 10;
            if (d == 6) {
                offset = (int) Math.pow(10, p);
            }
            num = num / 10;
            res = (int) Math.pow(10, p) * d + res;
            p++;
        }
        res = res + 3 * offset;
        return res;
    }
}