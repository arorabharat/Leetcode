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
            return this.isBadVersion(s) ? s : s + 1;
        }

        public int firstBadVersion(int n) {
            return binarySearch(1, n);
        }

        /**
         * iterative approach fits best in case of the lower bound
         */
        public int firstBadVersion2(int n) {
            int low = 1, high = n;
            int lastBadVersion = 0;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (isBadVersion(mid)) {
                    lastBadVersion = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            return lastBadVersion;
        }

        /**
         * Low , mid , high
         *
         * high - bad
         * low - good
         * mid could overlap with low or high
         *
         * 1 2 3 4 | 5 6 7 8
         * Lower bound without extra variable
         */
        public int firstBadVersion3(int n) {
            int low = 1;
            int high = n;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (isBadVersion(mid)) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }
    }
}