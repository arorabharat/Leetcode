import java.util.LinkedList;
import java.util.Queue;

class Solution_329 {
    public int longestIncreasingPath(int[][] matrix) {
        Graph graph = new Graph(matrix);
        return graph.dfs();
    }

    static class Graph {
        private final int[][] grid;
        private final int r;
        private final int c;
        private final int[] dr = {-1, 0, 1, 0};
        private final int[] dc = {0, 1, 0, -1};
        private boolean[][] visited;

        Graph(int[][] grid) {
            this.grid = grid;
            this.r = this.grid.length;
            if (this.r > 0) {
                this.c = this.grid[0].length;
            } else {
                this.c = 0;
            }
        }

        private boolean isEdge(int cr, int cc, int nr, int nc) {
            return this.grid[cr][cc] < this.grid[nr][nc];
        }

        boolean isValid(int i, int j) {
            return 0 <= i && i < this.r && 0 <= j && j < this.c;
        }

        public int dfs() {
            int maxPath = 0;
            for (int i = 0; i < this.r; i++) {
                for (int j = 0; j < this.c; j++) {
                    this.visited = new boolean[this.r][this.c];
                    int currPath = _dfs(i, j);
                    maxPath = Math.max(maxPath, currPath);
                }
            }
            return maxPath;
        }

        private int _dfs(int sr, int sc) {
            visited[sr][sc] = true;
            int maxChildPath = 0;
            for (int k = 0; k < 4; k++) {
                int nr = sr + dr[k];
                int nc = sc + dc[k];
                if (isValid(nr, nc) && isEdge(sr, sc, nr, nc) && !visited[nr][nc]) {
                    maxChildPath = Math.max(_dfs(nr, nc), maxChildPath);
                }
            }
            return 1 + maxChildPath;
        }
    }

    class Solution2 {

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        boolean isValid(int r, int c, int R, int C) {
            return 0 <= r && r < R && 0 <= c && c < C;
        }

        public int longestIncreasingPath(int[][] matrix) {
            int R = matrix.length;
            if (R == 0) {
                return 0;
            }
            int C = matrix[0].length;
            int[][] inDegree = new int[R][C];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    for (int d = 0; d < dr.length; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        if (isValid(nr, nc, R, C) && matrix[nr][nc] < matrix[r][c]) {
                            inDegree[nr][nc]++;
                        }
                    }
                }
            }
            Queue<int[]> q = new LinkedList<>();
            int[][] maxDis = new int[R][C];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (inDegree[r][c] == 0) {
                        q.add(new int[]{r, c, 0});
                    }
                }
            }
            int globalMaxDistance = 0;
            while (!q.isEmpty()) {
                int[] node = q.remove();
                int r = node[0];
                int c = node[1];
                int dis = node[2];
                for (int d = 0; d < dr.length; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (isValid(nr, nc, R, C) && matrix[nr][nc] > matrix[r][c]) {
                        inDegree[nr][nc]--;
                        maxDis[nr][nc] = Math.max(maxDis[nr][nc], dis + 1);
                        globalMaxDistance = Math.max(globalMaxDistance, maxDis[nr][nc]);
                        if (inDegree[nr][nc] == 0) {
                            q.add(new int[]{nr, nc, dis + 1});
                        }
                    }
                }
            }
            return globalMaxDistance;
        }

    }
}