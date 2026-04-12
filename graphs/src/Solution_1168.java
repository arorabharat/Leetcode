import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Solution_1168 {

    class Solution {

        class Edge {

            int to;
            int cost;

            public Edge(int to, int cost) {
                this.to = to;
                this.cost = cost;
            }
        }

        public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
            PriorityQueue<Edge> q = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
            List<List<Edge>> adj = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                adj.add(new ArrayList<>());
            }
            for (int[] pipe : pipes) {
                adj.get(pipe[0]).add(new Edge(pipe[1], pipe[2]));
                adj.get(pipe[1]).add(new Edge(pipe[0], pipe[2]));
            }
            for (int i = 0; i < n; i++) {
                q.add(new Edge((i + 1), wells[i]));
            }
            int totalCost = 0;
            boolean[] inMst = new boolean[n + 1];
            int count = 0;
            while (!q.isEmpty()) {
                Edge ne = q.poll();

                if (inMst[ne.to]) {
                    continue;
                }
                if (count == n) {
                    break;
                }
                totalCost = totalCost + ne.cost;
                inMst[ne.to] = true;
                count++;
                for (Edge e : adj.get(ne.to)) {
                    if (!inMst[e.to]) {
                        q.add(e);
                    }
                }
            }
            return totalCost;
        }
    }
}
