/**
 * https://leetcode.com/problems/search-a-2d-matrix-ii/
 */
class Solution_240 {

    // staricase traversal / two pointer traversal / space reduction, recommned
    class Solution1 {

        public boolean searchMatrix(int[][] matrix, int target) {
            int R = matrix.length;
            int C = matrix[0].length;
            int r = R - 1;
            int c = 0;
            while (c < C && r >= 0) {
                if (matrix[r][c] == target) {
                    return true;
                } else if (matrix[r][c] < target) {
                    c++;
                } else {
                    r--;
                }
            }
            return false;
        }
    }


    // sub-optimal
    class Solution2 {

        private boolean binarySearch(int[] arr, int s, int e, int key) {
            while (s <= e) {
                int m = s + (e - s) / 2;
                if (arr[m] == key) {
                    return true;
                } else if (arr[m] < key) {
                    s = m + 1;
                } else {
                    e = m - 1;
                }
            }
            return false;
        }

        /**
         * Time Complexity :  O( M*LogN )
         * Space Complexity :  O( 1 )
         */
        public boolean searchMatrix(int[][] matrix, int target) {
            int r = matrix.length;
            if (r == 0) {
                return false;
            }
            int c = matrix[0].length;
            if (c == 0) {
                return false;
            }
            for (int[] row : matrix) {
                if (row[0] <= target && target <= row[c - 1] && binarySearch(row, 0, c - 1, target)) {
                    return true;
                }
            }
            return false;
        }

    }
}

