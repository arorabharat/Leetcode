import java.util.*;


public class BellmanFord {

    static class Edge {
        int src, dest, weight;

        Edge(int s, int d, int w) {
            this.src = s;
            this.dest = d;
            this.weight = w;
        }
    }


    public static int[] bellmanFord(int V, List<Edge> edges, int source) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[source] = 0;

        // Relax edges V-1 times
        for (int i = 1; i < V; i++) {
            for (Edge e : edges) {
                if (dist[e.src] != Integer.MAX_VALUE &&
                        dist[e.src] + e.weight < dist[e.dest]) {

                    dist[e.dest] = dist[e.src] + e.weight;
                }
            }
        }

        // Detect negative cycle
        for (Edge e : edges) {
            if (dist[e.src] != Integer.MAX_VALUE &&
                    dist[e.src] + e.weight < dist[e.dest]) {

                System.out.println("Negative weight cycle detected");
                return null;
            }
        }

        return dist;
    }

    public static void main(String[] args) {

        int V = 5;

        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge(0, 1, 6));
        edges.add(new Edge(0, 2, 7));
        edges.add(new Edge(1, 2, 8));
        edges.add(new Edge(1, 3, 5));
        edges.add(new Edge(1, 4, -4));
        edges.add(new Edge(2, 3, -3));
        edges.add(new Edge(2, 4, 9));
        edges.add(new Edge(3, 1, -2));
        edges.add(new Edge(4, 3, 7));
        edges.add(new Edge(4, 0, 2));

        int[] result = bellmanFord(V, edges, 0);

        if (result != null) {
            for (int i = 0; i < result.length; i++) {
                System.out.println("Distance from 0 to " + i + " = " + result[i]);
            }
        }
    }
}
