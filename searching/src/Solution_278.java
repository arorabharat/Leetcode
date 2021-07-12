/**
 * https://leetcode.com/problems/first-bad-version/
 */
class Solution_278 {

    static class VersionControl {
        boolean isBadVersion(int m) {
            return m > 3;
        }
    }

    static class Solution extends VersionControl {

        /**
         * @see Level#GOOD
         * @see DSA#BINARY_SEARCH
         */
        public int binarySearch(int s, int e) {
            if (s < e) {
                // why we take mid using this approach is to prevent the overflow.
                // if we take mid by the approach (s+e)/2 when in case of the overflow and we would get wrong mid and algo would never stop
                int m = s + (e - s) / 2;
                if (this.isBadVersion(m)) {
                    return binarySearch(s, m - 1);
                } else {
                    return binarySearch(m + 1, e);
                }
            }
            return this.isBadVersion(s) ? s : s + 1 ;
        }

        public int firstBadVersion(int n) {
            return binarySearch(1, n);
        }
    }
}