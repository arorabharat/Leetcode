import java.util.*;

class DijkstraMinCostMinHop {

    static class State {

        int node;
        int dist;
        int hops;

        State(int node, int dist, int hops) {
            this.node = node;
            this.dist = dist;
            this.hops = hops;
        }
    }

    public static int[] dijkstra(int V, List<List<int[]>> graph, int src) {

        int[] dist = new int[V];
        int[] hops = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(hops, Integer.MAX_VALUE);

        dist[src] = 0;
        hops[src] = 0;

        PriorityQueue<State> pq = new PriorityQueue<>((s1, s2) -> {
                    if (s1.dist != s2.dist) {
                        return s1.dist - s2.dist;
                    }
                    return s1.hops - s2.hops;
                });

        pq.offer(new State(src, 0, 0));

        while (!pq.isEmpty()) {

            State cur = pq.poll();
            int u = cur.node;

            if (cur.dist > dist[u]) {
                continue;
            }

            for (int[] edge : graph.get(u)) {

                int v = edge[0];
                int weight = edge[1];

                int newDist = dist[u] + weight;
                int newHops = hops[u] + 1;

                if (newDist < dist[v] || (newDist == dist[v] && newHops < hops[v])) {
                    dist[v] = newDist;
                    hops[v] = newHops;
                    pq.offer(new State(v, newDist, newHops));
                }
            }
        }

        return dist;
    }
}

