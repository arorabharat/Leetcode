import java.util.ArrayList;
import java.util.List;

public class Solution_2101 {

    class Solution1 {

        class Graph {

            private final List<List<Integer>> adj;

            private final int n;

            public Graph(int n) {
                this.n = n;
                this.adj = new ArrayList<>();
                for (int u = 0; u < n; u++) {
                    this.adj.add(new ArrayList<>());
                }
            }

            void addEdge(int u, int v) {
                this.adj.get(u).add(v);
            }

            int dfs(int u, boolean[] visited) {
                visited[u] = true;
                int count = 1;
                for (int v : this.adj.get(u)) {
                    if (!visited[v]) {
                        count = count + dfs(v, visited);
                    }
                }
                return count;
            }

            int maxSizeOfConnectedComponent() {
                boolean[] visited = new boolean[this.n];
                int maxComponent = 0;
                for (int u = 0; u < this.n; u++) {
                    if (!visited[u]) {
                        maxComponent = Math.max(maxComponent, dfs(u, visited));
                    }
                }
                return maxComponent;
            }
        }

        boolean isWithinRadiusForFirstBomb(int[] b1, int[] b2) {
            int dx = b1[0] - b2[0];
            int dy = b1[1] - b2[2];
            double dx2 = Math.pow(dx, 2);
            double dy2 = Math.pow(dy, 2);
            return dx2 + dy2 <= Math.pow(b1[2], 2);
        }

        public int maximumDetonation(int[][] bombs) {
            int n = bombs.length;
            Graph graph = new Graph(n);
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (u != v && isWithinRadiusForFirstBomb(bombs[u], bombs[v])) {
                        graph.addEdge(u, v);
                    } else if (u != v && isWithinRadiusForFirstBomb(bombs[v], bombs[u])) {
                        graph.addEdge(v, u);
                    }
                }
            }
            return graph.maxSizeOfConnectedComponent();
        }
    }

}
