import java.util.*;

/**
 * https://leetcode.com/problems/connecting-cities-with-minimum-cost/
 *
 * @see DSA#PRIMS_ALGORITHM
 */
class Solution_1135 {

    static class Graph {

        final int NUM = 0;
        final int DISTANCE = 1;

        private final Map<Integer, List<int[]>> adj;
        private final int N;

        Graph(int N) {
            this.N = N;
            this.adj = new HashMap<>();
            for (int i = 1; i <= N; i++) {
                this.adj.put(i, new ArrayList<>());
            }
        }

        public void addEdge(int u, int v, int w) {
            this.adj.get(u).add(new int[]{v, w});
            this.adj.get(v).add(new int[]{u, w});
        }

        public void dfs(int u, boolean[] visited) {
            if (visited[u]) {
                return;
            }
            visited[u] = true;
            for (int[] node : this.adj.get(u)) {
                dfs(node[NUM], visited);
            }
        }

        public int numberOfConnectComponents() {
            boolean[] visited = new boolean[N + 1];
            int count = 0;
            for (int i = 1; i <= N; i++) {
                if (!visited[i]) {
                    count++;
                    dfs(i, visited);
                }
            }
            return count;
        }

        public int minimumSpanningTreeEdgeSum() {
            boolean[] visited = new boolean[N + 1];
            PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[DISTANCE]));
            minHeap.add(new int[]{1, 0});
            int numOfNodeLeft = N;
            int totalSumOfEdges = 0;
            while (!minHeap.isEmpty() && numOfNodeLeft > 0) {
                int[] currNode = minHeap.remove();
                if (!visited[currNode[NUM]]) {
                    visited[currNode[NUM]] = true;
                    numOfNodeLeft--;
                    totalSumOfEdges = totalSumOfEdges + currNode[DISTANCE];
                    for (int[] nextNode : this.adj.get(currNode[NUM])) {
                        if (!visited[nextNode[NUM]]) {
                            minHeap.add(new int[]{nextNode[NUM], nextNode[DISTANCE]});
                        }
                    }
                }
            }
            return totalSumOfEdges;
        }
    }

    public int minimumCost(int n, int[][] connections) {
        Graph graph = new Graph(n);
        for (int[] edge : connections) {
            graph.addEdge(edge[0], edge[1], edge[2]);
        }
        if (graph.numberOfConnectComponents() == 1) {
            return graph.minimumSpanningTreeEdgeSum();
        }
        return -1;
    }
}