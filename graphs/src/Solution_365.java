import java.util.*;

public class Solution_365 {

    class Solution1 {

        int X;
        int Y;
        int T;
        boolean[][] visited;
        Queue<int[]> queue;


        // Case - 1
        int[] fillFirst(int y) {
            return new int[]{this.X, y};
        }


        int[] fillSecond(int x) {
            return new int[]{x, this.Y};
        }

        // Case - 2
        int[] emptyFirst(int y) {
            return new int[]{0, y};
        }

        int[] emptySecond(int x) {
            return new int[]{x, 0};
        }

        // Case - 3
        int[] transferToFirst(int x, int y) {
            if (x + y > this.X) {
                return new int[]{this.X, y - (this.X - x)};
            } else {
                return new int[]{x + y, 0};
            }
        }

        int[] transferToSecond(int x, int y) {
            if (x + y > this.Y) {
                return new int[]{x - (this.Y - y), this.Y};
            } else {
                return new int[]{0, x + y};
            }
        }

        List<int[]> getNextState(int[] currState) {
            int x = currState[0];
            int y = currState[1];
            List<int[]> result = new ArrayList<>();
            result.add(fillFirst(y));
            result.add(fillSecond(x));
            result.add(emptyFirst(y));
            result.add(emptySecond(x));
            result.add(transferToFirst(x, y));
            result.add(transferToSecond(x, y));
            return result;
        }

        boolean isTargetState(int[] currState) {
            int x = currState[0];
            int y = currState[1];
            return x == T || y == T || (x + y == T);
        }

        boolean isVisited(int[] currState) {
            int x = currState[0];
            int y = currState[1];
            return visited[x][y];
        }

        void markVisited(int[] currState) {
            queue.add(currState);
            int x = currState[0];
            int y = currState[1];
            visited[x][y] = true;
        }

        public boolean canMeasureWater(int X, int Y, int target) {
            this.X = X;
            this.Y = Y;
            this.T = target;
            queue = new LinkedList<>();
            visited = new boolean[this.X + 1][this.Y + 1];
            markVisited(new int[]{0, 0});
            while (!queue.isEmpty()) {
                int[] currState = queue.remove();
                for (int[] nextState : getNextState(currState)) {
                    if (isTargetState(nextState)) {
                        return true;
                    }
                    if (!isVisited(nextState)) {
                        markVisited(nextState);
                    }
                }
            }
            return false;
        }
    }

    class Solution2 {
        public boolean canMeasureWater(int X, int Y, int target) {

            if (target > X + Y) return false;
            if (target == 0) return true;

            Queue<int[]> q = new LinkedList<>();
            boolean[][] vis = new boolean[X + 1][Y + 1];

            q.add(new int[]{0,0});
            vis[0][0] = true;

            while (!q.isEmpty()) {

                int[] cur = q.poll();
                int x = cur[0], y = cur[1];

                if (x == target || y == target || x + y == target)
                    return true;

                int[][] next = {
                        {X, y},
                        {x, Y},
                        {0, y},
                        {x, 0},
                        {Math.min(X, x+y), y - Math.min(X-x, y)},
                        {x - Math.min(Y-y, x), Math.min(Y, x+y)}
                };

                for (int[] n : next) {
                    if (!vis[n[0]][n[1]]) {
                        vis[n[0]][n[1]] = true;
                        q.add(n);
                    }
                }
            }
            return false;
        }
    }
}
