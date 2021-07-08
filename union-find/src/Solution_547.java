import java.util.*;

class Solution_547 {

    public int findCircleNum(int[][] M) {
        int n = M.length;
        if (n == 0) return 0;

        Graph g = new Graph(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] == 1) {
                    g.addEdge(i, j);
                }
            }
        }

        return g.components();
    }

    static class Graph {
        Map<Integer, List<Integer>> adj;
        int n;
        boolean[] visited;

        Graph(int n) {
            this.n = n;
            adj = new HashMap<>();
            for (int i = 0; i < n; i++) {
                adj.put(i, new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int components() {
            visited = new boolean[n];
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    bfs(i);
                    count++;
                }
            }
            return count;
        }

        void bfs(int s) {
            Queue<Integer> q = new LinkedList<>();
            q.add(s);
            visited[s] = true;
            while (!q.isEmpty()) {
                int front = q.remove();
                for (int v : adj.get(front)) {
                    if (!visited[v]) {
                        q.add(v);
                        visited[v] = true;
                    }
                }
            }
        }
    }
}
