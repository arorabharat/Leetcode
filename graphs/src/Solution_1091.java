import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/shortest-path-in-binary-matrix/
 *
 * @see DSA#DFS
 */
class Solution_1091 {

    private boolean isValid(int r, int c, int R, int C) {
        return 0 <= r && r < R && 0 <= c && c < C;
    }

    public int shortestPathBinaryMatrix(int[][] grid) {

        if (grid == null || grid.length == 0 || grid[0][0] == 1) {
            return -1;
        }
        int[] dr = {-1, -1, -1, 1, 1, 1, 0, 0};
        int[] dc = {-1, 0, 1, -1, 0, 1, 1, -1};
        int ROW = 0;
        int COL = 1;
        int R = grid.length;
        int C = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        int distance = 1;
        int currLevel = 1;
        int nextLevel = 0;
        while (!queue.isEmpty()) {
            int[] curr = queue.remove();
            currLevel--;
            if (curr[0] == R - 1 && curr[1] == C - 1) {
                return distance;
            }
            for (int k = 0; k < 8; k++) {
                int fr = dr[k] + curr[ROW];
                int fc = dc[k] + curr[COL];
                if (isValid(fr, fc, R, C) && grid[fr][fc] == 0) {
                    grid[fr][fc] = 1;
                    nextLevel++;
                    queue.add(new int[]{fr, fc});
                }
            }
            if (currLevel == 0) {
                currLevel = nextLevel;
                nextLevel = 0;
                distance++;
            }
        }
        return -1;
    }
}
