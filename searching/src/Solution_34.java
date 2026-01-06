/**
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 */
class Solution_34 {

    /**
     * @see DSA#BINARY_SEARCH
     * @see Level#GOOD
     */
    public int lowerBound(int[] nums, int t, int s, int e) {
        if (s < e) {
            int m = s + (e + 1 - s) / 2;
            if (nums[m] < t) {
                return lowerBound(nums, t, m, e);
            } else {
                return lowerBound(nums, t, s, m - 1);
            }
        }
        return s;
    }

    /**
     * @see DSA#BINARY_SEARCH
     * @see Level#GOOD
     */
    public int upperBound(int[] nums, int t, int s, int e) {
        if (s < e) {
            int m = s + (e - s) / 2;
            if (nums[m] <= t) {
                return upperBound(nums, t, m + 1, e);
            } else {
                return upperBound(nums, t, s, m);
            }
        }
        return s;
    }

    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int start = lowerBound(nums, target, -1, n - 1) + 1;
        int end = upperBound(nums, target, 0, n) - 1;
        int[] ans = new int[2];
        ans[0] = (start < n && target == nums[start]) ? start : -1;
        ans[1] = (end >= 0 && target == nums[end]) ? end : -1;
        return ans;
    }

    class Approach_1 {

        private int findLeftMostIndex(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;
            int leftMostIndex = -1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (nums[mid] > target) {
                    right = mid - 1;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    leftMostIndex = mid;
                    right = mid - 1;   // keep searching left
                }
            }
            return leftMostIndex;
        }

        private int findRightMostIndex(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;
            int rightMostIndex = -1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (nums[mid] > target) {
                    right = mid - 1;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    rightMostIndex = mid;
                    left = mid + 1;   // keep searching right
                }
            }
            return rightMostIndex;
        }

        public int[] searchRange(int[] nums, int target) {
            if (nums == null || nums.length == 0) {
                return new int[]{-1, -1};
            }

            int left = findLeftMostIndex(nums, target);
            if (left == -1) {
                return new int[]{-1, -1};
            }

            int right = findRightMostIndex(nums, target);
            return new int[]{left, right};
        }
    }
}


