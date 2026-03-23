import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: 23/03/26 - not able to solve , it is rooting problem in graph
public class Solution_2858 {

    class Graph {

        private Map<Integer, List<Integer>> adj;
        private Map<Integer, List<Integer>> revAdj;
        private Map<String, Integer> cache;
        private int n;


        Graph(int n) {
            this.adj = new HashMap<>();
            this.cache = new HashMap<>();
            this.n = n;
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

        int compute(int u, int v) {
            String key = u + "," + v;
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            int sum = 0;
            for (int w : this.adj.get(v)) {
                if (w != u) {
                    sum = sum + compute(v, w);
                }
            }
            for (int w : this.revAdj.get(v)) {
                if (w != u) {
                    sum = sum + compute(v, w) + 1;
                }
            }
            this.cache.put(key, sum);
            return sum;
        }

        // 3 <- 2 <- 1
        // -1 3
        // 3 2
        // 2 1


        int[] compute() {
            int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                compute(-1, i);
                int sum = 0;
                for (int w : this.adj.get(i)) {
                    sum = sum + this.cache.get(i + "," + w);
                }
                for (int w : this.revAdj.get(i)) {
                    sum = sum + this.cache.get(i + "," + w) + 1;
                }
                res[i] = sum;
            }
            return res;
        }
    }


    public int[] minEdgeReversals(int n, int[][] edges) {
        Graph graph = new Graph(n);
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.addEdge(u, v);
        }
        return graph.compute();
    }
}
