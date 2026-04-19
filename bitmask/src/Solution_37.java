import java.util.*;

public class Solution_37 {

    class Solution {

        List<List<Set<Character>>> possibleValues;
        boolean[][] visited;
        Queue<int[]> oneValueQ;
        int count = 0;
        int N = 9;

        void removeFromRow(int R, int C, char[][] board) {
            int v = board[R][C];
            for (int r = 0; r < N; r++) {
                if (r != R) {
                    possibleValues.get(r).get(C).remove(v);
                    addToQIfSizeIsOne(r, C, board);
                }
            }
        }

        private void addToQIfSizeIsOne(int r, int c, char[][] board) {
            if (possibleValues.size() == 1 && !visited[r][c]) {
                board[r][c] = possibleValues.get(r).get(c).stream().findFirst().get();
                oneValueQ.add(new int[]{r, c});
                visited[r][c] = true;
                count++;
            }
        }

        void removeFromCol(int R, int C, char[][] board) {
            char v = board[R][C];
            for (int c = 0; c < N; c++) {
                if (c != C) {
                    possibleValues.get(R).get(c).remove(v);
                    addToQIfSizeIsOne(R, c, board);
                }
            }
        }

        void removeFromBox(int R, int C, char[][] board) {
            char v = board[R][C];
            int startRow = 3 * (R / 3);
            int startCol = 3 * (C / 3);
            for (int r = startRow; r < startRow + 3; r++) {
                for (int c = startCol; c < startCol + 3; c++) {
                    if (r != R && c != C) {
                        possibleValues.get(r).get(c).remove(v);
                        addToQIfSizeIsOne(r, c, board);
                    }
                }
            }
        }

        public void solveSudoku(char[][] board) {
            this.oneValueQ = new LinkedList<>();
            this.visited = new boolean[N][N];
            this.count = 0;
            Set<Character> allNumbers = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                allNumbers.add((char) ('1' + i));
            }

            // N*N
            for (int r = 0; r < N; r++) {
                possibleValues.add(new ArrayList<>());
                for (int c = 0; c < N; c++) {
                    char v = board[r][c];
                    if (v != '.') {
                        possibleValues.get(r).get(c).addAll(allNumbers);
                    } else {
                        possibleValues.get(r).get(c).add(v);
                    }
                    addToQIfSizeIsOne(r, c, board);
                }
            }

            while (!oneValueQ.isEmpty()) {
                int[] cell = oneValueQ.remove();
                int r = cell[0];
                int c = cell[1];
                removeFromRow(r, c, board);
                removeFromCol(r, c, board);
                removeFromBox(r, c, board);
            }
            if (count != 81) {
                throw new RuntimeException("Something is left");
            }
        }
    }
}
