import java.util.*;

public class FloydWarshall {

    static final int INF = (int)1e9; // representation of infinity

    public static int[][] floydWarshall(int[][] graph) {
        int V = graph.length;

        // copy input graph to dist matrix
        int[][] dist = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        // core algorithm
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {

                    if (dist[i][k] == INF || dist[k][j] == INF)
                        continue;

                    dist[i][j] = Math.min(
                            dist[i][j],
                            dist[i][k] + dist[k][j]
                    );
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {

        int INF = (int)1e9;

        int[][] graph = {
                {0,   3,   INF, 7},
                {8,   0,   2,   INF},
                {5,   INF, 0,   1},
                {2,   INF, INF, 0}
        };

        int[][] result = floydWarshall(graph);

        // print result matrix
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {

                if (result[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }
}