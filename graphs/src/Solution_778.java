import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution_778 {

    private boolean validRange(int x, int y, int m, int n) {
        return 0 <= x && x < m && 0 <= y && y < n;
    }

    public int swimInWater(int[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        final int DIRECTIONS = 4;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        // minimum PriorityQueue
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparing(a -> a[2]));
        queue.add(new int[]{0, 0, 0});
        int[][] dis = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dis[i][j] = Integer.MAX_VALUE;
            }
        }
        dis[0][0] = grid[0][0];
        while (!queue.isEmpty()) {
            int[] front = queue.remove();
            int x = front[0];
            int y = front[1];
            int w = front[2];
            if (n == m - 1 && y == n - 1) {
                return w;
            }
            for (int i = 0; i < DIRECTIONS; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (!validRange(nx, ny, m, n)) continue;
                int edgeWeight = Math.max(grid[nx][ny] - Math.max(dis[x][y], grid[x][y]), 0);
                if (dis[x][y] + edgeWeight < dis[nx][ny]) {
                    dis[nx][ny] = dis[x][y] + edgeWeight;
                    queue.add(new int[]{nx, ny, dis[nx][ny]});
                }
            }
        }
        return dis[m - 1][n - 1];
    }
}