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


    class Excel2 {

        class Cell {
            int val = 0;
            Map<String, Integer> formula = new HashMap<>();
        }

        private Cell[][] sheet;
        private int H;
        private char W;

        public Excel2(int height, char width) {
            H = height;
            W = width;
            sheet = new Cell[H][W - 'A' + 1];

            for (int i = 0; i < H; i++) {
                for (int j = 0; j <= W - 'A'; j++) {
                    sheet[i][j] = new Cell();
                }
            }
        }

        public void set(int row, char column, int val) {
            Cell cell = sheet[row - 1][column - 'A'];
            cell.val = val;
            cell.formula.clear();
        }

        public int get(int row, char column) {
            return evaluate(row - 1, column - 'A');
        }

        public int sum(int row, char column, String[] numbers) {
            Cell cell = sheet[row - 1][column - 'A'];
            cell.formula.clear();

            for (String s : numbers) {
                if (!s.contains(":")) {
                    cell.formula.put(s, cell.formula.getOrDefault(s, 0) + 1);
                } else {
                    String[] parts = s.split(":");
                    int r1 = Integer.parseInt(parts[0].substring(1));
                    char c1 = parts[0].charAt(0);

                    int r2 = Integer.parseInt(parts[1].substring(1));
                    char c2 = parts[1].charAt(0);

                    for (int r = r1; r <= r2; r++) {
                        for (char c = c1; c <= c2; c++) {
                            String key = "" + c + r;
                            cell.formula.put(key, cell.formula.getOrDefault(key, 0) + 1);
                        }
                    }
                }
            }

            return evaluate(row - 1, column - 'A');
        }

        private int evaluate(int r, int c) {
            Cell cell = sheet[r][c];

            if (cell.formula.isEmpty()) {
                return cell.val;
            }

            int sum = 0;

            for (String key : cell.formula.keySet()) {
                int count = cell.formula.get(key);

                int row = Integer.parseInt(key.substring(1)) - 1;
                int col = key.charAt(0) - 'A';

                sum += count * evaluate(row, col);
            }

            cell.val = sum;
            return sum;
        }
    }

    class Excel3 {
        // do eager evaluation using BFS
        
    }
/**
 * Your Excel object will be instantiated and called as such:
 * Excel obj = new Excel(height, width);
 * obj.set(row,column,val);
 * int param_2 = obj.get(row,column);
 * int param_3 = obj.sum(row,column,numbers);
 */
}
