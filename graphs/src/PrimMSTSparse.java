import java.util.*;


// spare graph
class PrimMSTSparse {

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static int prim(int n, List<List<Edge>> graph) {

        boolean[] inMST = new boolean[n];

        PriorityQueue<Edge> pq =
                new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        pq.add(new Edge(0, 0));

        int totalCost = 0;

        while (!pq.isEmpty()) {

            Edge current = pq.poll();

            if (inMST[current.to]) {
                continue;
            }

            inMST[current.to] = true;
            totalCost += current.weight;

            for (Edge neighbor : graph.get(current.to)) {

                if (!inMST[neighbor.to]) {
                    pq.add(neighbor);
                }
            }
        }

        return totalCost;
    }
}


