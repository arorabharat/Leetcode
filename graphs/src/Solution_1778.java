import java.util.*;

public class Solution_1778 {

    class Solution {

        Set<String> visited;
        char[] directions = {'L', 'R', 'U', 'D'};
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        char[] revDirections = {'R', 'L', 'D', 'U'};

        // spiral order traversal
        int dfs(GridMaster master, int distance, List<Integer> index) {
            if (master.isTarget()) {
                return distance;
            }
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < 4; i++) {
                char dir = directions[i];
                char revDir = this.revDirections[i];
                List<Integer> nextIndex = List.of(index.get(0) + dx[i], index.get(1) + dy[1]);
                if (master.canMove(dir) && visited.contains(nextIndex.toString())) {
                    master.move(dir);
                    int targetDistance = dfs(master, distance + 1, index);
                    master.move(revDir);
                    if (targetDistance < minDistance) {
                        minDistance = targetDistance;
                    }
                }
            }
            return minDistance;
        }

        public int findShortestPath(GridMaster master) {
            visited = new HashSet<>();
            int targetDistance = dfs(master, 0, List.of(0, 0));
            if (targetDistance == Integer.MAX_VALUE) {
                return -1;
            }
            return targetDistance;
        }
    }

    interface GridMaster {
        boolean canMove(char direction);

        void move(char direction);

        boolean isTarget();
    }

    class Solution1 {


        int[][] grid = new int[1001][1001];
        boolean[][] visited = new boolean[1001][1001];
        char[] dir = {'U', 'D', 'L', 'R'};
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        char[] revDir = {'D', 'U', 'R', 'L'};

        void buildGrid(GridMaster master, int r, int c) {
            if (master.isTarget()) {
                grid[r][c] = 2;
            } else {
                grid[r][c] = 1;
            }
            visited[r][c] = true;
            for (int i = 0; i < dir.length; i++) {
                if (master.canMove(dir[i]) && !visited[r + dr[i]][c + dc[i]]) {
                    master.move(dir[i]);
                    buildGrid(master, r + dr[i], c + dc[i]);
                    master.move(revDir[i]);
                }
            }
        }

        public int findShortestPath(GridMaster master) {
            if (master.isTarget()) {
                return 0;
            }
            buildGrid(master, 500, 500);
            Queue<int[]> q = new LinkedList<>();
            visited = new boolean[1001][1001];
            q.add(new int[]{500, 500});
            visited[500][500] = true;
            int distance = 0;
            while (!q.isEmpty()) {
                int qSize = q.size();
                for (int i = 0; i < qSize; i++) {
                    int[] cell = q.remove();
                    int r = cell[0];
                    int c = cell[1];
                    for (int k = 0; k < dir.length; k++) {
                        int nr = r + dr[k];
                        int nc = c + dc[k];
                        if (grid[nr][nc] == 1 && !visited[nr][nc]) {
                            q.add(new int[]{nr, nc});
                            visited[nr][nc] = true;
                        } else if (grid[nr][nc] == 2) {
                            return distance + 1;
                        }
                    }
                }
                distance++;
            }
            return -1;
        }
    }
}
