import java.util.*;

/**
 * https://leetcode.com/problems/network-delay-time/
 */
class Solution_743 {

    static class Pair implements Comparable<Pair> {
        int v;
        int w;

        Pair(int v, int w) {
            this.w = w;
            this.v = v;
        }

        @Override
        public int compareTo(Pair p) {
            return this.w - p.w;
        }
    }

    static class Graph {
        Map<Integer, List<Pair>> g;
        int n;
        int[] dis;

        Graph(int n) {
            this.n = n;
            this.g = new HashMap<>();
            for (int i = 1; i <= n; i++) {
                g.put(i, new ArrayList<>());
            }
        }

        void addEdge(int u, int v, int w) {
            g.get(u).add(new Pair(v, w));
        }

        int dijkstra(int start) {
            dis = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                dis[i] = Integer.MAX_VALUE;
            }
            Queue<Pair> q = new PriorityQueue<>();
            q.add(new Pair(start, 0));
            dis[start] = 0;
            while (!q.isEmpty()) {
                Pair up = q.remove();
                int u = up.v;
                for (Pair vp : g.get(u)) {
                    int v = vp.v;
                    int w = vp.w;
                    if (dis[v] > dis[u] + w) {
                        dis[v] = dis[u] + w;
                        q.add(new Pair(v, dis[v]));
                    }
                }
            }
            int ans = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++) {
                ans = Math.max(ans, dis[i]);
            }
            if (ans == Integer.MAX_VALUE) {
                return -1;
            }
            return ans;
        }
    }

    public int networkDelayTime(int[][] times, int N, int K) {
        Graph graph = new Graph(N);
        int e = times.length;
        for (int[] time : times) {
            graph.addEdge(time[0], time[1], time[2]);
        }
        return graph.dijkstra(K);
    }

    /**
     * ========================= Bellman ford algorithm ===============================
     * Ww iterate through all the edges v - 1 tine
     * in each iteration we relax the edges if possible
     * <p>
     * <p>
     * <p>
     * proof
     * at phase k of v -1 steps , all the nodes whose shortest path is of size k will have correct value
     * above is true for k = 0 , source node only
     * after step one - all the nodes which are relaxed will be in of the two categories,
     * similarly in next step all the edges with path size two will be there
     * <p>
     * if the negative cycle is not there- max path size could be v -1
     * hence all the edges which could have correct value will have correct value
     * we need remove the edges which have negative loop ,
     * if we try to relax again now then only negative edge cycle will be impacted, just mark them infinity
     * <p>
     * there are three possible cased
     * no path - remain infinity in all steps
     * a path with k edges
     * a path with negative edge weight cycle
     */
    public int networkDelayTime2(int[][] times, int N, int K) {
        int[] dis = new int[N + 1];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[K] = 0;
        for (int i = 1; i < N; i++) {
            for (int[] edge : times) {
                int u = edge[0];
                int v = edge[1];
                int e = edge[2];
                if (dis[u] != Integer.MAX_VALUE && dis[u] + e < dis[v]) {
                    dis[v] = dis[u] + e;
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            max = Math.max(max, dis[i]);
        }
        return max == Integer.MAX_VALUE ? -1 : max;
    }
}
