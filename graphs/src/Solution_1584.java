import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/min-cost-to-connect-all-points/
 * prims algorithm
 */
class Solution_1584 {

    // approach 1 : prims algorithm
    public int minCostConnectPoints1(int[][] points) {
        Graph graph = new Graph(points.length, points);
        return graph.minSpanTree();
    }

    // approach 2 : is the kruskal approach which should not be used in dense tree, like in the current case
    // it used only for demonstration purpose
    public int minCostConnectPoints(int[][] points) {
        Graph graph = new Graph(points.length, points);
        return graph.kruskal();
    }

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
        int n;
        int[][] points;

        Graph(int n, int[][] points) {
            this.n = n;
            this.points = points;
        }

        int distance(int x, int y) {
            int dx = Math.abs(points[x][0] - points[y][0]);
            int dy = Math.abs(points[x][1] - points[y][1]);
            return dx + dy;
        }

        int minSpanTree() {
            return this.prims(0);
        }

        /**
         * ============================== Prim's algoritm ===============================
         */
        int prims(int start) {
            // lazy implementation of prim's algorithm
            boolean[] visited = new boolean[this.n];
            int count = this.n;
            // comparator & lambda expression
            PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(b -> b[1])); // min heap
            queue.add(new int[]{start, 0});
            int sum = 0;
            while (count > 0 && !queue.isEmpty()) {
                int[] pair = queue.remove();
                int curr = pair[0];
                if (visited[curr]) {
                    continue;
                }
                visited[curr] = true;
                sum += pair[1];
                count--;
                for (int next = 0; next < this.n; next++) {
                    if (curr != next && !visited[next]) {
                        queue.add(new int[]{next, distance(curr, next)});
                    }
                }
            }
            return sum;
        }

        /**
         * ================================== Kruskal algorithm ==========================
         */
        int kruskal() {
            int V = points.length;
            // create a sorted set of edges
            PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(b -> b[2]));
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (i != j) {
                        queue.add(new int[]{i, j, distance(i, j)});
                    }
                }
            }
            int count = 0;
            int sum = 0;
            // initialise the forest of vertices , where each vertex is individual vertex
            DisJointSet disJointSet = new DisJointSet(V);
            while (count < V - 1) {
                // pick the smallest edge if the end points does not belong to same set
                int[] edge = queue.remove();
                if (!disJointSet.isSameSet(edge[0], edge[1])) {
                    sum += edge[2];
                    disJointSet.union(edge[0], edge[1]);
                    count++;
                }
            }
            return sum;
        }

    }
}