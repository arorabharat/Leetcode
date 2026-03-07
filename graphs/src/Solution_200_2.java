import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/number-of-islands/
 * grid bfs and counting number of components
 *
 * @see DSA#BINARY_SEARCH
 */
class Solution_2 {

    enum Direction {

        L(0, -1),
        R(0, 1),
        U(-1, 0),
        D(1, 0);

        final int dr;
        final int dc;

        Direction(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }

        public int getDr() {
            return dr;
        }

        public int getDc() {
            return dc;
        }
    }

    class Graph {

        char[][] grid;
        boolean[][] visited;
        int R;
        int C;

        public Graph(char[][] grid) {
            this.grid = grid;
            this.R = grid.length;
            this.C = grid[0].length;
            this.visited = new boolean[R][C];
        }

        boolean isValid(int r, int c) {
            return 0 <= r && r < this.R && 0 <= c && c < this.C;
        }

        void bfs(int sr, int sc) {
            Queue<int[]> q = new LinkedList<>();
            addToQueue(sr, sc, q);
            while (!q.isEmpty()) {
                int[] front = q.remove();
                int r = front[0];
                int c = front[1];
                for (Direction direction : Direction.values()) {
                    int nr = r + direction.dr;
                    int nc = c + direction.dc;
                    if (isValid(nr, nc) && isUnvisited(nr, nc) && isLand(nr, nc)) {
                        addToQueue(nr, nc, q);
                    }
                }
            }
        }

        private void addToQueue(int r, int c, Queue<int[]> q) {
            q.add(new int[]{r, c});
            visited[r][c] = true;
        }

        boolean isLand(int r, int c) {
            return grid[r][c] == '1';
        }

        boolean isUnvisited(int r, int c) {
            return !visited[r][c];
        }

        int graphCount() {
            visited = new boolean[R][C];
            int islandCount = 0;
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (!visited[r][c] && isLand(r, c)) {
                        islandCount++;
                        bfs(r, c);
                    }
                }
            }
            return islandCount;
        }

    }


    public int numIslands(char[][] grid) {
        int R = grid.length;
        if (R == 0) {
            return 0;
        }
        int C = grid[0].length;
        if (C == 0) {
            return 0;
        }
        Graph graph = new Graph(grid);
        return graph.graphCount();
    }
}