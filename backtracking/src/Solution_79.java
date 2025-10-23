class Solution_79 {

    private final int[] dx = {1, -1, 0, 0};
    private final int[] dy = {0, 0, 1, -1};
    private int m;
    private int n;
    private char[][] board;
    private boolean[][] visited;
    private char[] word;

    boolean dfs(int i, int j, int k) {
        if (i >= this.m || j >= this.n || i < 0 || j < 0 || k > word.length) {
            return false;
        }
        if (visited[i][j]) {
            return false;
        }
        if (board[i][j] != word[k]) {
            return false;
        }
        if (k == word.length - 1) {
            return true;
        }
        visited[i][j] = true;
        for (int d = 0; d < 4; d++) {
            if (dfs(i + this.dx[d], j + this.dy[d], k + 1)) {
                visited[i][j] = false;
                return true;
            }
        }
        visited[i][j] = false;
        return false;
    }

    public boolean exist(char[][] board, String wordStr) {
        this.board = board;
        this.m = board.length;
        if (m == 0) return false;
        this.n = board[0].length;
        this.visited = new boolean[m][n];
        this.word = wordStr.toCharArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
}
