import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/min-cost-to-connect-all-points/
 * prims algorithm
 *
 * @see DSA#MINIMUM_SPANNING_TREE
 * @see DSA#DISJOINT_SET
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

        /**
         * Time Complexity :  O( 1 ) amortized
         */
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
         * Time Complexity :  O( N^2 )
         * Space Complexity :  O( N )
         */
        int prims(int start) {
            // lazy implementation of prim's algorithm
            boolean[] visited = new boolean[this.n];
            int numOfPointsLeftToVist = this.n;
            PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(b -> b[1]));
            minHeap.add(new int[]{start, 0});
            int sumOfEdges = 0;
            while (numOfPointsLeftToVist > 0 && !minHeap.isEmpty()) {
                int[] pointAndDistance = minHeap.remove();
                int currPoint = pointAndDistance[0];
                if (!visited[currPoint]) {
                    visited[currPoint] = true;
                    sumOfEdges += pointAndDistance[1];
                    numOfPointsLeftToVist--;
                    // for loop is there because the pairs are connected
                    // otherwise we only iterate through the edges which are connected to current point
                    for (int next = 0; next < this.n; next++) {
                        if (!visited[next]) {
                            minHeap.add(new int[]{next, distance(currPoint, next)});
                        }
                    }
                }
            }
            return sumOfEdges;
        }

        /**
         * ================================== Kruskal algorithm ==========================
         * Time Complexity :  O( E.Log(E) ) or O( E.Log(V) )
         * Space Complexity :  O( E + V )
         */
        int kruskal() {
            final int START = 0;
            final int END = 1;
            final int DISTANCE = 2;
            int V = points.length;
            // create a set S containing all the edges in the graph
            PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(b -> b[DISTANCE]));
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (i != j) {
                        queue.add(new int[]{i, j, distance(i, j)});
                    }
                }
            }
            int edgesInSpanningTree = 0;
            int totalSumOfEdges = 0;
            // create a forest F (a set of trees), where each vertex in the graph is a separate tree
            DisJointSet disJointSet = new DisJointSet(V);
            while (edgesInSpanningTree < V - 1) {
                // remove an edge with minimum weight from S
                int[] edge = queue.remove();
                // if the removed edge connects two different trees then add it to the forest F, combining two trees into a single tree
                if (!disJointSet.isSameSet(edge[START], edge[END])) {
                    totalSumOfEdges += edge[DISTANCE];
                    disJointSet.union(edge[START], edge[END]);
                    edgesInSpanningTree++;
                }
            }
            return totalSumOfEdges;
        }

    }
}