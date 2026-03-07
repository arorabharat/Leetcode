import java.util.*;

public class Solution_947 {

    // [[0,0],[0,1],
    //  [1,0],      [1,2],
    //        [2,1],[2,2]]

    class Graph {

        Map<Integer, List<Integer>> adj;
        private boolean[] visited;
        final int N;

        public Graph(int[][] stones) {
            adj = new HashMap<>();
            this.N = stones.length;
            for (int i = 0; i < this.N; i++) {
                this.adj.put(i, new ArrayList<>());
            }

            // build graph
            for (int i = 0; i < this.N; i++) {
                for (int j = i + 1; j < this.N; j++) {
                    int[] s1 = stones[i];
                    int[] s2 = stones[j];
                    if (isConnected(s1, s2)) {
                        addEdge(i, j);
                    }
                }
            }
        }

        boolean isConnected(int[] a, int[] b) {
            return a[0] == b[0] || a[1] == b[1];
        }

        void addEdge(int i, int j) {
            this.adj.get(i).add(j);
            this.adj.get(j).add(i);
        }


        int connectedComponents() {
            this.visited = new boolean[this.N];
            int count = 0;
            for (int i = 0; i < this.N; i++) {
                if (!visited[i]) {
                    dfs(i);
                    count++;
                }
            }
            return count;
        }

        void dfs(int u) {
            visited[u] = true;
            for (int v : this.adj.get(u)) {
                if (!visited[v]) {
                    dfs(v);
                }
            }
        }

    }

    public int removeStones(int[][] stones) {
        int N = stones.length;
        Graph graph = new Graph(stones);
        return graph.connectedComponents();
    }
}
