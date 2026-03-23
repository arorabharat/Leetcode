import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/sum-of-distances-in-tree/
 */
class Solution_834 {

    class Solution1 {

        class Graph {

            int n;
            int[] dis;
            int[] childrenCount;
            Map<Integer, List<Integer>> adj;

            Graph(int n) {
                this.n = n;
                this.dis = new int[n];
                this.childrenCount = new int[n];
                this.adj = new HashMap<>();
                for (int i = 0; i < n; i++) {
                    this.adj.put(i, new ArrayList<>());
                }
            }

            void addEdge(int u, int v) {
                this.adj.get(u).add(v);
                this.adj.get(v).add(u);
            }


            void rooting(int u, int p, int d) {
                int count = 0;
                for (int v : this.adj.get(u)) {
                    if (v != p) {
                        rooting(v, u, d + 1);
                        count = count + childrenCount[v];
                    }
                }
                dis[0] += d; // add distance of each node
                childrenCount[u] = count + 1;
            }

            void propagation(int u, int p) {
                if (p != -1) {
                    dis[u] = dis[p] + (n - 2 * childrenCount[u]);
                }
                for (int v : this.adj.get(u)) {
                    if (v != p) {
                        propagation(v, u);
                    }
                }
            }

            int[] computeDistanceSum() {
                rooting(0, -1, 0);
                for (int i = 0; i < n; i++) {
                    System.out.println(childrenCount[i]);
                }
                propagation(0, -1);
                return dis;
            }
        }

        public int[] sumOfDistancesInTree(int n, int[][] edges) {

            Graph graph = new Graph(n);
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                graph.addEdge(u, v);
            }
            return graph.computeDistanceSum();
        }
    }

    // optmised version
    class Solution2 {

        int n;
        int[] dis;
        int[] subtreeSize;
        List<Integer>[] adj;

        public int[] sumOfDistancesInTree(int n, int[][] edges) {

            this.n = n;
            dis = new int[n];
            subtreeSize = new int[n];

            adj = new ArrayList[n];
            for(int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();

            for(int[] e : edges){
                adj[e[0]].add(e[1]);
                adj[e[1]].add(e[0]);
            }

            dfs1(0, -1, 0);   // compute subtree sizes + dis[0]
            dfs2(0, -1);      // reroot DP

            return dis;
        }

        void dfs1(int u, int parent, int depth){

            subtreeSize[u] = 1;
            dis[0] += depth;

            for(int v : adj[u]){

                if(v == parent) continue;

                dfs1(v, u, depth + 1);

                subtreeSize[u] += subtreeSize[v];
            }
        }

        void dfs2(int u, int parent){

            for(int v : adj[u]){

                if(v == parent) continue;

                dis[v] = dis[u] + (n - 2 * subtreeSize[v]);

                dfs2(v, u);
            }
        }
    }
}


