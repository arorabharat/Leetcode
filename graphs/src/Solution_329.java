class Solution_329 {
    static class Graph {
        private final int[][] grid;
        private final int r;
        private final int c;
        private boolean[][] visited;
        private final int[] dr = {-1, 0, 1, 0};
        private final int[] dc = {0, 1, 0, -1};

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

    public int longestIncreasingPath(int[][] matrix) {
        Graph graph = new Graph(matrix);
        return graph.dfs();
    }
}