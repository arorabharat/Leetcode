import java.util.*;

public class Solution_1778 {

    class Solution {

        interface GridMaster {
            boolean canMove(char direction);

            void move(char direction);

            boolean isTarget();
        }

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
            int targetDistance = dfs(master, 0, new int[]{0, 0});
            if (targetDistance == Integer.MAX_VALUE) {
                return -1;
            }
            return targetDistance;
        }
    }
}
