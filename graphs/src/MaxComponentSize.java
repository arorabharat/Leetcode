import java.util.*;

public class MaxComponentSize {

    private final int n;
    private final List<List<Integer>> adj;

    public MaxComponentSize(int n) {

        this.n = n;

        adj = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    // directed edge
    public void addDirectedEdge(int u, int v) {
        adj.get(u).add(v);
    }

    // undirected edge
    public void addUndirectedEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    public List<Integer> neighbors(int node) {
        return adj.get(node);
    }

    public int size() {
        return n;
    }

    // =============================
    // TEMPLATE 1
    // Directed graph
    // max reachable nodes
    // =============================

    public int maxReachDirected() {

        int maxSize = 0;

        for (int i = 0; i < n; i++) {

            boolean[] visited = new boolean[n];

            int size = dfs(i, visited);

            maxSize = Math.max(maxSize, size);
        }

        return maxSize;
    }

    // =============================
    // TEMPLATE 2
    // Undirected graph
    // max connected component
    // =============================

    public int maxComponentUndirected() {

        boolean[] visited = new boolean[n];

        int maxSize = 0;

        for (int i = 0; i < n; i++) {

            if (!visited[i]) {

                int size = dfs(i, visited);

                maxSize = Math.max(maxSize, size);
            }
        }

        return maxSize;
    }

    // =============================
    // DFS
    // =============================

    private int dfs(int node, boolean[] visited) {

        visited[node] = true;

        int size = 1;

        for (int nei : adj.get(node)) {

            if (!visited[nei]) {

                size += dfs(nei, visited);
            }
        }

        return size;
    }
}


