import java.util.*;

public class Solution_1557 {

    class Solution {

        class Graph {

            int n;
            Map<Integer, List<Integer>> adj;
            boolean[] visited;
            int[] inDegree;
            Set<Integer> zeroInDegreeSet;

            public Graph(int n) {
                this.n = n;
                this.visited = new boolean[n];
                this.adj = new HashMap<>();
                this.zeroInDegreeSet = new HashSet<>();
                this.inDegree = new int[n];
                for (int u = 0; u < n; u++) {
                    this.adj.put(u, new ArrayList<>());
                    zeroInDegreeSet.add(u);
                }
            }

            void addDirectedEdge(int u, int v) {
                this.adj.get(u).add(v);
                this.inDegree[v]++;
                this.zeroInDegreeSet.remove(v);
            }

            // O(V)
            void dfs(int u) {
                visited[u] = true;
                for (int v : this.adj.get(u)) {
                    if (!visited[v]) {
                        dfs(v);
                    }
                }
            }

            List<Integer> minimalReachableSet() {
                List<Integer> minimalSet = new ArrayList<>(zeroInDegreeSet);
                for (int u : zeroInDegreeSet) {
                    dfs(u);
                }
                for (int u = 0; u < n; u++) {
                    if (!visited[u]) {
                        dfs(u);
                        minimalSet.add(u);
                    }
                }
                return minimalSet;
            }

        }

        public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
            Graph graph = new Graph(n);
            for (List<Integer> edge : edges) {
                graph.addDirectedEdge(edge.get(0), edge.get(1));
            }
            return graph.minimalReachableSet();
        }
    }
}
