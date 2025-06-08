public class Solution_88 {
    class Solution {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            int[] arr = new int[m + n];
            while (i1 < m && i2 < n) {
                arr[i3++] = (nums1[i1] <= nums2[i2]) ? nums1[i1++] : nums2[i2];
            }
            while (i1 < m) {
                arr[i3++] = nums1[i1++];
            }
            while (i2 < n) {
                arr[i3++] = nums2[i2++];
            }
            i3 = 0;
            while (i3 < m + n) {
                nums1[i3] = arr[i3];
            }
        }
    }
}
