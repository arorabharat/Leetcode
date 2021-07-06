import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/is-graph-bipartite/
 * check if the graph is bipartite
 */
class Solution_785 {

    /**
     * ========================== Is graph bipartite ============================
     */
    public boolean isBipartite(int[][] graph) {
        int V = graph.length;
        Graph g = new Graph(V);
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                g.addEdge(i, graph[i][j]);
            }
        }
        return g.bfs();
    }

    static class Graph {
        int V;
        List<List<Integer>> adj;
        int[] color;

        Graph(int V) {
            this.V = V;
            color = new int[this.V];
            adj = new ArrayList<>();
            for (int i = 0; i < this.V; i++) {
                adj.add(new ArrayList<>());
            }
        }

        boolean bfs() {
            for (int i = 0; i < this.V; i++) {
                if (color[i] == 0) {
                    if (!_bfs(i)) {
                        return false;
                    }
                }
            }
            // clear the memory
            for (int i = 0; i < this.V; i++) {
                color[i] = 0;
            }
            return true;
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        boolean _bfs(int s) {
            color[s] = 1;
            Queue<Integer> q = new LinkedList<>();
            q.add(s);
            while (!q.isEmpty()) {
                int u = q.remove();
                for (int v : adj.get(u)) {
                    if (color[v] == 0) {
                        q.add(v);
                        color[v] = -1 * color[u];
                    } else if (color[u] == color[v]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
