import java.util.*;

class LongestPathKahnAlgo {

    public int longestPath(int n, List<List<Integer>> adj) {

        int[] indegree = new int[n];

        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) {
                indegree[v]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();

        // start from all sources
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        int[] dist = new int[n]; // longest path ending at node

        while (!q.isEmpty()) {

            int u = q.poll();

            for (int v : adj.get(u)) {

                dist[v] = Math.max(dist[v], dist[u] + 1);

                if (--indegree[v] == 0) {
                    q.add(v);
                }
            }
        }

        int ans = 0;
        for (int d : dist) ans = Math.max(ans, d);

        return ans;
    }
}
