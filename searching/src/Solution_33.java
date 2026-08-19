class Solution_33 {

    class Solution1 {

        int getRotationIndex(int[] nums, int s, int e) {
            if (s == e) {
                return s;
            } else if (s < e) {
                int m = s + (e - s) / 2;
                if (nums[s] <= nums[m]) {
                    return (m + 1 == nums.length || nums[m + 1] < nums[m]) ? m : getRotationIndex(nums, m + 1, e);
                } else {
                    return getRotationIndex(nums, s, m - 1);
                }
            } else {
                return -1;
            }
        }

        int binarySearch(int[] nums, int s, int e, int t) {
            if (s == e) {
                return nums[s] == t ? s : -1;
            } else if (s < e) {
                int m = s + (e - s) / 2;
                if (nums[m] == t) {
                    return m;
                } else if (nums[m] < t) {
                    return binarySearch(nums, m + 1, e, t);
                } else {
                    return binarySearch(nums, s, m - 1, t);
                }
            } else {
                return -1;
            }
        }

        public int search(int[] nums, int target) {
            int index = getRotationIndex(nums, 0, nums.length - 1);
            int leftSearch = binarySearch(nums, 0, index, target);
            if (leftSearch != -1) {
                return leftSearch;
            }
            return binarySearch(nums, index + 1, nums.length - 1, target);
        }
    }

    class Solution2 {
        public int search(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] == target) {
                    return mid;
                }
                // Left half is sorted
                if (nums[left] <= nums[mid]) {
                    if (target >= nums[left] && target < nums[mid]) {
                        right = mid - 1; // target is in the left sorted half
                    } else {
                        left = mid + 1;
                    }
                }
                // Right half is sorted
                else {
                    if (target > nums[mid] && target <= nums[right]) {
                        left = mid + 1; // target is in the right sorted half
                    } else {
                        right = mid - 1;
                    }
                }
            }
            return -1;
        }
    }
}
