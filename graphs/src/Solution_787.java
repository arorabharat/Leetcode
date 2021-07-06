import java.util.*;

// TODO pending
public class Solution_787 {

    /**
     * We have to go from src to dst in the cheapest price and having not more than k stops
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Graph graph = new Graph(n);
        for (int[] flight : flights) {
            graph.addEdge(flight[0], flight[1], flight[2]);
        }
        return graph.cheapestFlight(src, dst, k);
    }

    static class Graph {
        List<List<int[]>> adj;
        int n;

        Graph(int n) {
            this.n = n;
            adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v, int w) {
            this.adj.get(u).add(new int[]{v, w});
        }

        int cheapestFlight(int src, int dst, int k) {
            int[] dis = new int[n];
            Arrays.fill(dis, Integer.MAX_VALUE);
            dis[src] = 0;
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(0);
            int count = 0;
            int nextLevel = 0;
            int currLevel = 1;
            while (!queue.isEmpty() && count <= k) {
                int u = queue.remove();
                currLevel--;
                for (int[] list : this.adj.get(u)) {
                    int v = list[0];
                    int p = list[1];
                    if (dis[u] + p < dis[v]) {
                        dis[v] = dis[u] + p;
                        nextLevel++;
                    }
                    queue.add(v);
                }
                if (currLevel == 0) {
                    currLevel = nextLevel;
                    nextLevel = 0;
                    count++;
                }
            }
            return dis[dst] == Integer.MAX_VALUE ? -1 : dis[dst];
        }
    }
}