/**
 * https://leetcode.com/problems/search-a-2d-matrix-ii/
 */
class Solution_240 {

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


    /**
     * TWo pointer approach
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        int R = matrix.length;
        if (R == 0) {
            return false;
        }
        int C = matrix[0].length;
        if (C == 0) {
            return false;
        }
        int r = R - 1;
        int c = 0;
        while (r >= 0 && c < C) {
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

