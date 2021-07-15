/**
 * https://leetcode.com/problems/median-of-two-sorted-arrays/
 */
class Solution_4 {

    private int lowerBound(int[] arr, int s, int e, int t) {
        if (s < e) {
            int m = s + (e + 1 - s) / 2;
            if (arr[m] >= t) {
                return lowerBound(arr, s, m - 1, t);
            } else {
                return lowerBound(arr, m, e, t);
            }
        }
        return s;
    }

    private int search(int[] nums1, int s, int e, int[] nums2, int t) {
        if (s <= e) {
            int m = s + (e - s) / 2;
            int n = lowerBound(nums2, -1, nums2.length - 1, nums1[m]) + 1;
            if (m + n + 1 == t) {
                return nums1[m];
            } else if (m + n + 1 < t) {
                return search(nums1, m + 1, e, nums2, t);
            } else {
                return search(nums1, s, m - 1, nums2, t);
            }
        }
        return Integer.MIN_VALUE;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if ((m + n) % 2 != 0) {
            int midIndex = (m + n + 1) / 2;
            int mid = search(nums1, 0, nums1.length - 1, nums2, midIndex);
            if (mid == Integer.MIN_VALUE) {
                mid = search(nums2, 0, nums2.length - 1, nums1, midIndex);
            }
            return mid;
        } else {
            int leftMidIndex = (m + n + 1) / 2;
            int rightMidIndex = (m + n + 2) / 2;
            int leftMid = search(nums1, 0, nums1.length - 1, nums2, leftMidIndex);
            int rightMid = search(nums1, 0, nums1.length - 1, nums2, rightMidIndex);
            if (leftMid == Integer.MIN_VALUE) {
                leftMid = search(nums2, 0, nums2.length - 1, nums1, leftMidIndex);
            }
            if (rightMid == Integer.MIN_VALUE) {
                rightMid = search(nums2, 0, nums2.length - 1, nums1, rightMidIndex);
            }
            return ((double) leftMid + (double) rightMid) / 2;

        }
        // return (leftMid + rightMid)/2;
        // int[] arr = {1, 4 , 6, 8, 12};
        // int n = arr.length;
        // System.out.println(lowerBound(arr,-1,n-1,15));
    }
}