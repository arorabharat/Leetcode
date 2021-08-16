import java.util.*;

/**
 * https://leetcode.com/problems/connecting-cities-with-minimum-cost/
 *
 * @see DSA#PRIMS_ALGORITHM
 * @see DSA#KRUSKAL_ALGORITHM
 */
class Solution_1135 {


    static class DisJointSet {

        private final int[] set;
        private final int[] rank;

        DisJointSet(int n) {
            set = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                set[i] = i;
            }
        }

        int get(int a) {
            // path compression
            return set[a] = (set[a] == a) ? a : get(set[a]);
        }

        void union(int a, int b) {
            int pa = get(a);
            int pb = get(b);
            if (pa != pb) {
                if (rank[pa] == rank[pb]) {
                    rank[pa]++;
                }
                if (rank[pa] > rank[pb]) {
                    set[pb] = pa;
                } else {
                    set[pa] = pb;
                }
            }
        }

        boolean isSameSet(int a, int b) {
            return get(a) == get(b);
        }
    }

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

        public int minimumSpanningTreeEdgeSumPrims() {
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

        public int minimumSpanningTreeEdgeSumKruskal() {
            // create a set S containing all the edges in the graph
            PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
            for (int u : this.adj.keySet()) {
                for (int[] edge : this.adj.get(u)) {
                    minHeap.add(new int[]{u, edge[0], edge[1]});
                }
            }
            // create a forest F (a set of trees), where each vertex in the graph is a separate tree
            DisJointSet disJointSet = new DisJointSet(N + 1);
            int totalSumOfEdges = 0;
            int totalNumOfEdges = 0;
            // we need to iterate this loop if we have less than N - 1 edges once we reach N - 1 edges then we already have minimum spanning tree
            while (!minHeap.isEmpty() && totalNumOfEdges < N - 1) {
                // remove an edge with minimum weight from S
                int[] currEdge = minHeap.remove();
                // if the removed edge connects two different trees then add it to the forest F, combining two trees into a single tree
                if (!disJointSet.isSameSet(currEdge[0], currEdge[1])) {
                    disJointSet.union(currEdge[0], currEdge[1]);
                    totalSumOfEdges = totalSumOfEdges + currEdge[2];
                    totalNumOfEdges++;
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
            return graph.minimumSpanningTreeEdgeSumPrims();
        }
        return -1;
    }
}