import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/number-of-islands/
 * grid bfs and counting number of components
 *
 * @see DSA#BINARY_SEARCH
 */
class Solution_200 {

    public int numIslands(char[][] grid) {
        Graph graph = new Graph(grid);
        return graph.bfs();
    }

    static class Graph {

        private final char[][] grid;
        private final boolean[][] visited;
        private final int r;
        private final int c;
        private final int[] dr = {-1, 0, 1, 0};
        private final int[] dc = {0, 1, 0, -1};

        Graph(char[][] grid) {
            this.grid = grid;
            this.r = this.grid.length;
            if (this.r != 0) {
                this.c = this.grid[0].length;
            } else {
                this.c = 0;
            }
            this.visited = new boolean[this.r][this.c];
        }

        /**
         * @see DSA#BFS
         * @see DSA#NUMBER_OF_CONNECTED_COMPONENTS
         */
        int bfs() {
            int count = 0;
            for (int i = 0; i < this.r; i++) {
                for (int j = 0; j < this.c; j++) {
                    if (!visited[i][j] && grid[i][j] == '1') {
                        _bfs(i, j);
                        count++;
                    }
                }
            }
            return count;
        }

        boolean isValid(int i, int j) {
            return 0 <= i && i < this.r && 0 <= j && j < this.c;
        }

        boolean isEdge(int cr, int cc, int nr, int nc) {
            return grid[cr][cc] == '1' && grid[nr][nc] == '1';
        }

        private void _bfs(int sr, int sc) {
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{sr, sc});
            visited[sr][sc] = true;
            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                for (int k = 0; k < 4; k++) {
                    int cr = cell[0];
                    int cc = cell[1];
                    int nr = cr + dr[k];
                    int nc = cc + dc[k];
                    if (isValid(nr, nc) && !visited[nr][nc] && isEdge(cr, cc, nr, nc)) {
                        queue.add(new int[]{nr, nc});
                        visited[nr][nc] = true;
                    }
                }
            }
        }
    }

    /**
     * @see DSA#DFS
     */
    void dfs(char[][] grid, int r, int c) {
        int nr = grid.length;
        int nc = grid[0].length;

        if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0';
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
    }

    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    dfs(grid, r, c);
                }
            }
        }
        return num_islands;
    }
}