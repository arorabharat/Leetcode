import java.util.*;

public class BellmanFordKEdges {

    class Edge {
        int u, v, w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static final int INF = (int)1e9;

    public static int shortestPathAtMostKEdges(
            int n,
            List<Edge> edges,
            int src,
            int dst,
            int K) {

        int[] prev = new int[n];
        Arrays.fill(prev, INF);

        prev[src] = 0;

        for (int i = 1; i <= K; i++) {

            int[] curr = Arrays.copyOf(prev, n);

            for (Edge e : edges) {

                if (prev[e.u] != INF) {
                    curr[e.v] = Math.min(curr[e.v], prev[e.u] + e.w);
                }
            }

            prev = curr;
        }

        return prev[dst] == INF ? -1 : prev[dst];
    }
}