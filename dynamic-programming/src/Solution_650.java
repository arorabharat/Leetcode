/**
 * https://leetcode.com/problems/2-keys-keyboard/
 */
class Solution_650 {

    /**
     * minSteps(f) + n/f
     */
    public int minSteps(int n) {
        if (n == 1) return 0;
        for (int j = n - 1; j >= 1; j--) {
            if (n % j == 0) {
                return minSteps(j) + n / j;
            }
        }
        return n;
    }
}