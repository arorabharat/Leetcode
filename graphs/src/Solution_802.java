import java.util.*;

public class Solution_802 {

    class Solution2 {

        boolean dfs(int[][] graph, int u, int[] coloring, boolean[] partOfCycle) {
            coloring[u] = 1;
            boolean cycle = false;
            for (int j = 0; j < graph[u].length; j++) {
                int v = graph[u][j];
                if (coloring[v] == 0 && dfs(graph, v, coloring, partOfCycle)) {
                    partOfCycle[u] = true;
                    cycle = true;
                } else if (coloring[v] == 1) {
                    partOfCycle[u] = true;
                    cycle = true;
                }
            }
            coloring[u] = 2;
            return cycle;
        }

        public List<Integer> eventualSafeNodes(int[][] graph) {
            int n = graph.length;
            boolean[] partOfCycle = new boolean[n];
            int[] coloring = new int[n];
            dfs(graph, 0, coloring, partOfCycle);
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!partOfCycle[i]) {
                    result.add(i);
                }
            }
            return result;
        }
    }

    class Solution {

        class Graph {

            Map<Integer, List<Integer>> adj;
            Map<Integer, List<Integer>> revAdj;
            int n;

            public Graph(int n) {
                this.n = n;
                this.adj = new HashMap<>();
                this.revAdj = new HashMap<>();
                for (int i = 0; i < n; i++) {
                    this.adj.put(i, new ArrayList<>());
                    this.revAdj.put(i, new ArrayList<>());
                }
            }

            void addEdge(int u, int v) {
                this.adj.get(u).add(v);
                this.revAdj.get(v).add(u);
            }

            void dfs1(int u, boolean[] visited, Stack<Integer> traversalOrder) {
                visited[u] = true;
                for (int v : this.adj.get(u)) {
                    if (!visited[v]) {
                        dfs1(v, visited, traversalOrder);
                    }
                }
                traversalOrder.add(u);
            }

            void dfs2(int u, boolean[] visited, Set<Integer> sccNode) {
                visited[u] = true;
                for (int v : this.revAdj.get(u)) {
                    if (!visited[v]) {
                        dfs2(v, visited, sccNode);
                    }
                }
                sccNode.add(u);
            }

            List<Integer> getMinNodes() {
                boolean[] visited = new boolean[this.n];
                Stack<Integer> traversalOrder = new Stack<>();
                for (int u = 0; u < this.n; u++) {
                    if (!visited[u]) {
                        dfs1(u, visited, traversalOrder);
                    }
                }
                System.out.println(traversalOrder.toString());
                visited = new boolean[this.n];
                List<Integer> minNodes = new ArrayList<>();
                while (!traversalOrder.isEmpty()) {
                    int u = traversalOrder.pop();
                    Set<Integer> sccNode = new HashSet<>();
                    if (!visited[u]) {
                        dfs2(u, visited, sccNode);
                        if (sccNode.size() == 1) {
                            minNodes.addAll(sccNode);
                        }
                    }
                }
                Collections.sort(minNodes);
                return minNodes;
            }
        }

        public List<Integer> eventualSafeNodes(int[][] graph) {
            Graph g = new Graph(graph.length);
            for (int u = 0; u < graph.length; u++) {
                for (int v : graph[u]) {
                    g.addEdge(u, v);
                }
            }
            return g.getMinNodes();
        }
    }
}
