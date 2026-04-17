import java.util.*;

public class BellmanFordExactKEdges {

    static final int INF = (int)1e9;

    class Edge {
        int u, v, w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    public static int shortestPathExactlyKEdges(
            int n,
            List<Edge> edges,
            int src,
            int dst,
            int K) {

        int[][] dp = new int[K+1][n];

        for (int i = 0; i <= K; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][src] = 0;

        for (int i = 1; i <= K; i++) {

            for (Edge e : edges) {
                if (dp[i-1][e.u] != INF) {
                    dp[i][e.v] = Math.min(dp[i][e.v], dp[i-1][e.u] + e.w);
                }
            }
        }

        return dp[K][dst] == INF ? -1 : dp[K][dst];
    }
}