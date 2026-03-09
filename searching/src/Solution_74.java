public class Solution_74 {


    class Solution {

        int R;
        int C;

        int row(int i) {
            return i / this.C;
        }

        int col(int i) {
            return i % this.C;
        }

        public boolean searchMatrix(int[][] matrix, int target) {
            this.R = matrix.length;
            this.C = matrix[0].length;
            int s = 0;
            int e = this.R * this.C - 1;
            while (s <= e) {
                int m = s + (e - s) / 2;
                int r = row(m);
                int c = col(m);
                if (target == matrix[r][c]) {
                    return true;
                } else if (matrix[r][c] < target) {
                    s = m + 1;
                } else {
                    e = m - 1;
                }
            }
            return false;
        }
    }
}
