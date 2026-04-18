public class Solution_36 {

    class Solution {


        int getIthBitSet(int x, int i) {
            return x & 1 << i;
        }

        int setIthBitSet(int x, int i) {
            return x | 1 << i;
        }

        public boolean isValidSudoku(char[][] board) {

            int n = board.length;
            int[] row = new int[n];
            int[] col = new int[n];
            int[] box = new int[n];
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    int rv = row[r];
                    int cv = col[c];
                    int v = board[r][c];
                    if (v > 9 || v < 1) {
                        return false;
                    }
                    if (getIthBitSet(rv, v) == 1) {
                        return false;
                    }
                    row[r] = setIthBitSet(rv, v);
                    if (getIthBitSet(cv, v) == 1) {
                        return false;
                    }
                    col[c] = setIthBitSet(cv, v);
                    int b = 3 * (r / 3) + (c / 3);
                    int bv = box[b];
                    if (getIthBitSet(bv, v) == 1) {
                        return false;
                    }
                    box[b] = setIthBitSet(bv, v);
                }
            }
            return true;
        }
    }
}
