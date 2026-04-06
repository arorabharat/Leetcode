import java.util.*;

public class Solution_802 {

    class Solution2 {

        boolean dfs(int[][] graph, int u, int[] coloring, boolean[] partOfCycle) {
            coloring[u] = 1;
            boolean cycle = false;
            for (int j = 0; j < graph[u].length; j++) {
                int v = graph[u][j];
                if(coloring[v] == 0 && dfs(graph, v, coloring, partOfCycle)) {
                    partOfCycle[u] = true;
                    cycle = true;
                } else if(coloring[v] == 1) {
                    partOfCycle[u] = true;
                    cycle = true;
                }
            }
            coloring[u] = 2;
            return cycle;
        }

        public List<Integer> eventualSafeNodes(int[][] graph) {
            int n = graph.length;
            boolean[] partOfCycle = new boolean[n];
            int[] coloring = new int[n];
            dfs(graph, 0, coloring,partOfCycle);
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if(!partOfCycle[i]) {
                    result.add(i);
                }
            }
            return result;
        }
    }
}
