import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/01-matrix/
 * BFS with multiple starting point
 */
class Solution_542 {

    public int[][] updateMatrix(int[][] matrix) {
        Graph g = new Graph(matrix);
        return g.solve();
    }

    static class Pair {
        int i;
        int j;

        Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    static class Graph {
        final int R;
        final int C;
        int[][] g;
        // up right bottom left
        int[] di = {-1, 0, 1, 0};
        int[] dj = {0, 1, 0, -1};


        Graph(int[][] g) {
            this.g = g;
            this.R = g.length;
            this.C = (R == 0) ? 0 : g[0].length;
        }

        boolean inRange(int i, int j) {
            return 0 <= i && i < R && 0 <= j && j < C;
        }

        List<Pair> adj(int i, int j) {
            List<Pair> list = new ArrayList<>();
            for (int k = 0; k < di.length; k++) {
                int ni = i + di[k];
                int nj = j + dj[k];
                if (inRange(ni, nj)) {
                    list.add(new Pair(ni, nj));
                }

            }
            return list;
        }

        List<Pair> zeroIndices() {
            List<Pair> list = new ArrayList<>();
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (g[i][j] == 0) {
                        list.add(new Pair(i, j));
                    }
                }
            }
            return list;
        }

        int[][] solve() {
            List<Pair> zeros = zeroIndices();
            return bfs(zeros);
        }


        int[][] bfs(List<Pair> zeros) {
            Queue<Pair> q = new LinkedList<>();
            int[][] dis = new int[R][C];
            boolean[][] visited = new boolean[R][C];

            for (Pair p : zeros) {
                visited[p.i][p.j] = true;
                dis[p.i][p.j] = 0;
                q.add(p);
            }

            while (!q.isEmpty()) {
                Pair curr = q.remove();
                for (Pair p : adj(curr.i, curr.j)) {
                    if (!visited[p.i][p.j]) {
                        q.add(p);
                        visited[p.i][p.j] = true;
                        dis[p.i][p.j] = dis[curr.i][curr.j] + 1;
                    }
                }

            }
            return dis;
        }

    }
}