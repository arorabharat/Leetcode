public class Solution_73 {

    class Solution1 {


        public void setZeroes(int[][] matrix) {
            int R = matrix.length;
            int C = matrix[0].length;
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (matrix[r][c] == 0) {
                        matrix[r][0] = 0;
                        matrix[0][c] = 0;
                    }
                }
            }
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (matrix[r][0] == 0 || matrix[0][c] == 0) {
                        matrix[r][c] = 0;
                    }
                }
            }
        }
    }

    class Solution2 {

        public void setZeroes(int[][] matrix) {
            int R = matrix.length;
            int C = matrix[0].length;
            boolean rowZero = false;
            boolean colZero = false;

            for (int r = 0; r < R; r++) {
                if (matrix[r][0] == 0) {
                    colZero = true;
                    break;
                }
            }

            for (int c = 0; c < C; c++) {
                if (matrix[0][c] == 0) {
                    rowZero = true;
                    break;
                }
            }

            for (int r = 1; r < R; r++) {
                for (int c = 1; c < C; c++) {
                    if (matrix[r][c] == 0) {
                        matrix[0][c] = 0;
                        matrix[r][0] = 0;
                    }
                }
            }

            for (int r = 1; r < R; r++) {
                for (int c = 1; c < C; c++) {
                    if (matrix[0][c] == 0 || matrix[r][0] == 0) {
                        matrix[r][c] = 0;
                    }
                }
            }

            for (int c = 0; c < C; c++) {
                if (rowZero) {
                    matrix[0][c] = 0;
                }
            }

            for (int r = 0; r < R; r++) {
                if (colZero) {
                    matrix[r][0] = 0;
                }
            }
        }
    }
}
