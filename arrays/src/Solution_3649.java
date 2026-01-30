import java.util.Arrays;

public class Solution_3649 {

    // brute force approach
    class Approach_1 {

        private int abs(int a) {
            return Math.abs(a);
        }

        private int min(int a, int b) {
            return Math.min(a, b);
        }

        private int max(int a, int b) {
            return Math.max(a, b);
        }

        private boolean criteria(int a, int b) {
            return min(abs(a - b), abs(a + b)) <= min(abs(a), abs(b)) &&
                    max(abs(a - b), abs(a + b)) >= max(abs(a), abs(b));

        }

        public long perfectPairs(int[] nums) {
            int n = nums.length;
            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (criteria(nums[i], nums[j])) {
                        count++;
                    }
                }
            }
            return count;
        }
    }

    class Approach_2 {

        // I did not understand the reason but the expression could be reduced to
        // arr[j] <= 2* arr[i];
        // where arr[i] = abs(nums[i])

        private int binarySearch(int[] arr, int s, int e, long ele) {
            // 0 1
            int lastMatch = s - 1;
            while (s < e) {
                int m = (e + s + 1) / 2;
                if ((long) arr[m] <= 2 * ele) {
                    s = m;
                    lastMatch = m;
                } else {
                    e = m - 1;
                }
            }
            return ((long) arr[s] <= 2 * ele) ? s : lastMatch;
        }

        public long perfectPairs(int[] nums) {
            int n = nums.length;
            int[] arr = Arrays.stream(nums).map(Math::abs).toArray();
            Arrays.sort(arr);
            long count = 0;
            for (int i = 0; i < n - 1; i++) {
                int index = binarySearch(arr, i + 1, n - 1, arr[i]);
                count = count + index - i;
            }
            return count;
        }
    }
}


