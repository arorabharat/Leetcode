import java.util.HashMap;
import java.util.Map;

public class Solution_631 {

    class Excel {

        int height;
        int width;
        int[][] mat;
        Map<Integer, Map<Integer, String[]>> formula;

        private int getC(char col) {
            return col - 'A';
        }

        private int getR(int row) {
            return row - 1;
        }

        public Excel(int height, char width) {
            this.width = getC(width) + 1;
            this.height = getR(height) + 1;
            this.mat = new int[this.height][this.width];
            this.formula = new HashMap<>();
        }

        public void set(int row, char column, int val) {
            int r = getR(row);
            int c = getC(column);
            this.mat[r][c] = val;
            if (this.formula.containsKey(r)) {
                this.formula.get(r).remove(c);
            }
        }

        int[] getLocation(String cell) {
            char col = cell.charAt(0);
            int row = Integer.parseInt(cell.substring(1));
            return new int[]{getR(row), getC(col)};
        }

        String[] getRangeCells(String range) {
            return range.split(":");
        }

        boolean isRange(String range) {
            return range.contains(":");
        }

        public int dfs(int r, int c) {
            if (!this.formula.containsKey(r) || !this.formula.get(r).containsKey(c)) {
                return mat[r][c];
            }
            int sum = 0;
            String[] expressionList = this.formula.get(r).get(c);
            for (String expr : expressionList) {
                if (isRange(expr)) {
                    String[] range = getRangeCells(expr);
                    int[] startCell = getLocation(range[0]);
                    int[] endCell = getLocation(range[1]);
                    int sr = startCell[0];
                    int sc = startCell[1];
                    int er = endCell[0];
                    int ec = endCell[1];
                    for (int nr = sr; nr <= er; nr++) {
                        for (int nc = sc; nc <= ec; nc++) {
                            sum = sum + dfs(nr, nc);
                        }
                    }
                } else {
                    // cell
                    int[] loc = getLocation(expr);
                    sum = sum + dfs(loc[0], loc[1]);
                }
            }
            return sum;
        }

        public int get(int row, char column) {
            int r = getR(row);
            int c = getC(column);
            return dfs(r, c);
        }

        public int sum(int row, char column, String[] numbers) {
            int r = getR(row);
            int c = getC(column);
            this.formula.put(r, this.formula.getOrDefault(r, new HashMap<>()));
            Map<Integer, String[]> formulaRow = this.formula.get(r);
            formulaRow.put(c, numbers);
            return dfs(r, c);
        }
    }
/**
 * Your Excel object will be instantiated and called as such:
 * Excel obj = new Excel(height, width);
 * obj.set(row,column,val);
 * int param_2 = obj.get(row,column);
 * int param_3 = obj.sum(row,column,numbers);
 */
}
