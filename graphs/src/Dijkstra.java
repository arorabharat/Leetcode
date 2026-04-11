import java.util.*;

class Dijkstra {

    static class Edge {

        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static int[] shortestPath(int n,
                                     List<List<Edge>> graph,
                                     int source) {

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[source] = 0;

        PriorityQueue<Edge> pq =
                new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        pq.add(new Edge(source, 0));

        while (!pq.isEmpty()) {

            Edge current = pq.poll();

            int node = current.to;
            int currentDist = current.weight;

            if (currentDist > dist[node]) {
                continue;
            }

            for (Edge neighbor : graph.get(node)) {

                int next = neighbor.to;
                int newDist = currentDist + neighbor.weight;

                if (newDist < dist[next]) {

                    dist[next] = newDist;

                    pq.add(new Edge(next, newDist));
                }
            }
        }

        return dist;
    }
}