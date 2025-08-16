import java.util.LinkedList;
import java.util.Queue;

public class Solution_695 {

    private final int[] dr = {-1, 1, 0, 0};
    private final int[] dc = {0, 0, -1, 1};
    private boolean[][] visited;

    int _bfs(int r, int c, int[][] grid, int m, int n) {
        visited[r][c] = true;
        Queue<int[]> q = new LinkedList<>();
        if (grid[r][c] == 0) {
            return 0;
        }
        q.add(new int[]{r, c});
        int count = 1;
        while (!q.isEmpty()) {
            int[] node = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = node[0] + dr[d];
                int nc = node[1] + dc[d];
                if (!(0 <= nr && nr < m) || !(0 <= nc && nc < n)) continue;
                if (!visited[nr][nc] && grid[nr][nc] == 1) {
                    q.add(new int[]{nr, nc});
                    count++;
                    visited[nr][nc] = true;
                }
            }
        }
        return count;
    }

    int bfs(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        visited = new boolean[m][n];
        int maxArea = 0;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                int currentMaxArea = _bfs(r, c, grid, m, n);
                maxArea = Math.max(currentMaxArea, maxArea);
            }
        }
        return maxArea;
    }

    public int maxAreaOfIsland(int[][] grid) {
        return bfs(grid);
    }
}
