import java.util.List;

/**
 * https://leetcode.com/problems/optimal-account-balancing/
 * TODO
 */
class Solution_465 {

    static class Graph {

        int N;
        int M = 30;
        int[][] adj = new int[M][M];
        boolean[] visited;
        boolean[] stack;

        Graph() {
        }

        void addEdge(int u, int v, int w) {
            adj[v][u] = w;
        }

        boolean detectCycle(int s) {
            stack[s] = true;
            for (int i = 0; i < M; i++) {
                if (adj[s][i] != 0) {
                    if (!visited[i]) {
                        if (detectCycle(i)) {
                            stack[s] = false;
                            return true;
                        }
                    } else {
                        stack[s] = false;
                        return stack[i];
                    }
                }
            }
            stack[s] = false;
            return false;
        }
    }

    public int minTransfers(int[][] transactions) {
        int n = transactions.length;
        Graph graph = new Graph();
        for (int[] transaction : transactions) {
            int u = transaction[0];
            int v = transaction[1];
            int w = transaction[2];
            graph.addEdge(u, v, w);
        }
        System.out.println(graph.detectCycle(0));
        return 0;
    }
}
