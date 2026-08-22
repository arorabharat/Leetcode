import java.util.*;

public class Solution_36 {

    class Solution3 {

        public boolean isValidSudoku(char[][] board) {
            int R = board.length;
            if (R != 9) {
                return false;
            }
            int C = board[0].length;
            if (C != 9) {
                return false;
            }
            // pass 1
            boolean[] visited = new boolean[R + 1];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (board[r][c] == '.') {
                        continue;
                    }
                    if (isVisited(r, c, visited, board)) {
                        return false;
                    }
                }
            }
            // pass 2
            visited = new boolean[R + 1];
            for (int c = 0; c < C; c++) {
                for (int r = 0; r < R; r++) {
                    if (board[r][c] == '.') {
                        continue;
                    }
                    if (isVisited(r, c, visited, board)) return false;
                }
            }
            visited = new boolean[R + 1];
            // pass 3
            for (int r = 0; r < R; r = r + 3) {
                for (int c = 0; c < C; c = c + 3) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (board[r + i][c + j] == '.') {
                                continue;
                            }
                            if (isVisited(r + i, c + j, visited, board)){
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }

        private static boolean isVisited(int r, int c, boolean[] visited, char[][] board) {
            int val = board[r][c] - '0';
            if (visited[val]) {
                return true;
            }
            visited[val] = true;
            return false;
        }
    }

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

    class Solution2 {

        Map<Integer, Set<String>> dp;

        Set<String> _para(int n) {
            if (dp.containsKey(n)) {
                return dp.get(n);
            }
            Set<String> results = new HashSet<>();
            if (n == 1) {
                results.add("()");
                dp.put(n, results);
                return results;
            }
            for (int i = 1; i < n; i++) {
                Set<String> part1 = _para(i);
                Set<String> part2 = _para(n - i);
                for (String p1 : part1) {
                    for (String p2 : part2) {
                        results.add(p1 + p2);
                    }
                }
            }
            if (n > 1) {
                Set<String> part3 = _para(n - 1);
                for (String p3 : part3) {
                    results.add("(" + p3 + ")");
                }
            }
            dp.put(n, results);
            return results;
        }

        public List<String> generateParenthesis(int n) {
            dp = new HashMap<>();
            return new ArrayList<>(_para(n));
        }
    }
}
