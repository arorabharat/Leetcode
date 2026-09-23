import java.util.*;

public class Solution_934 {
    class Solution {

        record Pair(int r, int c) {
        }

        int[] dr = {0, 0, -1, 1};
        int[] dc = {-1, 1, 0, 0};
        int R;
        int C;

        boolean isValid(int r, int c) {
            return 0 <= r && r < R && 0 <= c && c < C;
        }

        void bfs(Set<Pair> startNode, Pair p, boolean[][] visited, int[][] grid) {
            Queue<Pair> q = new LinkedList<>();
            q.add(p);
            visited[p.r][p.c] = true;
            startNode.add(p);
            while (!q.isEmpty()) {
                Pair f = q.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = f.r + dr[d];
                    int nc = f.c + dc[d];
                    if (isValid(nr, nc) && !visited[nr][nc] && grid[nr][nc] == 1) {
                        Pair np = new Pair(nr, nc);
                        q.add(np);
                        visited[nr][nc] = true;
                        startNode.add(np);
                    }
                }
            }
        }

        public int shortestBridge(int[][] grid) {

            Set<Pair> start = new HashSet<>();
            Set<Pair> target = new HashSet<>();


            R = grid.length;
            if (R == 0) {
                throw new IllegalArgumentException("row size can not be zero");
            }
            C = grid[0].length;

            if (C == 0) {
                throw new IllegalArgumentException("col size can not be zero");
            }

            boolean[][] visited = new boolean[R][C];

            boolean isFirst = true;

            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (!visited[r][c] && grid[r][c] == 1) {
                        if (isFirst) {
                            isFirst = false;
                            bfs(start, new Pair(r, c), visited, grid);
                        } else {
                            bfs(target, new Pair(r, c), visited, grid);
                        }
                    }
                }
            }

            Queue<Pair> q = new LinkedList<>(start);
            int distance = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    Pair f = q.poll();
                    for (int d = 0; d < 4; d++) {
                        assert f != null;
                        int nr = f.r + dr[d];
                        int nc = f.c + dc[d];
                        if (!isValid(nr, nc)) {
                            continue;
                        }
                        if (!visited[nr][nc] && grid[nr][nc] == 0) {
                            Pair np = new Pair(nr, nc);
                            q.add(np);
                            visited[nr][nc] = true;
                        }
                        if (target.contains(new Pair(nr, nc))) {
                            return distance;
                        }
                    }
                }
                distance++;
            }
            return -1;
        }

    }

    class Solution2 {

        private static final int[] DR = {-1, 1, 0, 0};
        private static final int[] DC = {0, 0, -1, 1};

        public int shortestBridge(int[][] grid) {
            int rows = grid.length;
            int cols = grid[0].length;

            boolean[][] visited = new boolean[rows][cols];
            Deque<int[]> firstIsland = new ArrayDeque<>();

            // Phase 1: find the first island, mark it visited, collect its cells.
            outer:
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] == 1 && !visited[r][c]) {
                        collectIsland(grid, visited, r, c, firstIsland);
                        break outer;
                    }
                }
            }

            // Phase 2: multi-source BFS from the first island across water.
            Deque<int[]> queue = firstIsland;
            int distance = 0;

            while (!queue.isEmpty()) {
                int size = queue.size();
                distance++;

                for (int i = 0; i < size; i++) {
                    int[] cell = queue.poll();
                    int r = cell[0];
                    int c = cell[1];

                    for (int d = 0; d < 4; d++) {
                        int nr = r + DR[d];
                        int nc = c + DC[d];

                        if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                        if (visited[nr][nc]) continue;

                        // Reached the second island.
                        if (grid[nr][nc] == 1) return distance;

                        // Expand across water.
                        visited[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                    }
                }
            }

            return -1; // unreachable given problem guarantees
        }

        // BFS/DFS to mark one island and collect its cells.
        private void collectIsland(int[][] grid, boolean[][] visited,
                                   int startR, int startC, Deque<int[]> island) {
            int rows = grid.length;
            int cols = grid[0].length;

            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{startR, startC});
            visited[startR][startC] = true;

            while (!stack.isEmpty()) {
                int[] cell = stack.pop();
                int r = cell[0];
                int c = cell[1];
                island.add(cell);

                for (int d = 0; d < 4; d++) {
                    int nr = r + DR[d];
                    int nc = c + DC[d];
                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                    if (visited[nr][nc] || grid[nr][nc] == 0) continue;

                    visited[nr][nc] = true;
                    stack.push(new int[]{nr, nc});
                }
            }
        }
    }
}
