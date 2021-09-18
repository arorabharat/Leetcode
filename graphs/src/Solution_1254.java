import java.util.LinkedList;
import java.util.Queue;

public class Solution_1254 {

    private boolean[][] visited;
    private final Queue<int[]> q = new LinkedList<>();
    private final static int[] dr = {1, -1, 0, 0};
    private final static int[] dc = {0, 0, 1, -1};
    private int R;
    private int C;

    private boolean valid(int r, int c) {
        return 0 < r && r < R && 0 < c && c < C;
    }

    void bfs(int[][] grid) {
        while (!q.isEmpty()) {
            int[] cell = q.remove();
            for (int k = 0; k < 4; k++) {
                int r = cell[0] + dr[k];
                int c = cell[1] + dc[k];
                if (valid(r, c) && !visited[r][c] && grid[r][c] == 0) {
                    q.add(new int[]{r, c});
                    visited[r][c] = true;
                }
            }
        }
    }

    public int closedIsland(int[][] grid) {
        R = grid.length;
        C = grid[0].length;
        visited = new boolean[R][C];
        for (int c = 0; c < C; c++) {
            if (grid[0][c] == 0) {
                visited[0][c] = true;
                q.add(new int[]{0, c});
            }
            if (grid[R - 1][c] == 0) {
                visited[R - 1][c] = true;
                q.add(new int[]{R - 1, c});
            }
        }
        for (int r = 0; r < R; r++) {
            if (grid[r][0] == 0) {
                visited[r][0] = true;
                q.add(new int[]{r, 0});
            }
            if (grid[r][C - 1] == 0) {
                visited[r][C - 1] = true;
                q.add(new int[]{r, C - 1});
            }
        }
        bfs(grid);
        int count = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (!visited[r][c] && grid[r][c] == 0) {
                    q.add(new int[]{r, c});
                    bfs(grid);
                    count++;
                }
            }
        }
        return count;
    }
}
