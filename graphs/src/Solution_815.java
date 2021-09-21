import java.util.*;

class Solution_815 {

    public int bfs(Set<Integer> start, Set<Integer> end, int[][] g, int m) {
        Queue<Integer> q = new LinkedList<>();
        Set<Integer> v = new HashSet<>();

        int[] dist = new int[m];
        for (int i = 0; i < m; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        for (Integer x : start) {
            if (end.contains(x)) {
                return 1;
            }
            v.add(x);
            dist[x] = 1;
            q.add(x);
        }

        while (!q.isEmpty()) {
            int f = q.remove();
            for (int i = 0; i < m; i++) {

                if (g[f][i] == 1) {
                    if (end.contains(i)) {
                        return dist[f] + 1;
                    }
                    if (!v.contains(i)) {
                        v.add(i);
                        q.add(i);
                        dist[i] = dist[f] + 1;
                    }
                }
            }
        }
        return -1;
    }

    public int numBusesToDestination(int[][] routes, int S, int T) {
        int m = routes.length;
        if (m == 0) return -1;
        if (S == T) return 0;
        int[][] g = new int[m][m];
        Map<Integer, List<Integer>> map = new HashMap<>();
        Set<Integer> start = new HashSet<>();
        Set<Integer> end = new HashSet<>();
        for (int i = 0; i < m; i++) {
            int n = routes[i].length;
            for (int j = 0; j < n; j++) {
                if (S == routes[i][j]) {
                    start.add(i);
                }
                if (T == routes[i][j]) {
                    end.add(i);
                }
                if (!map.containsKey(routes[i][j])) {
                    map.put(routes[i][j], new ArrayList<>());
                }
                map.get(routes[i][j]).add(i);
            }
        }

        for (Integer x : map.keySet()) {
            List<Integer> gl = map.get(x);
            for (int i = 0; i < gl.size(); i++) {
                for (int j = i + 1; j < gl.size(); j++) {
                    g[gl.get(i)][gl.get(j)] = 1;
                    g[gl.get(j)][gl.get(i)] = 1;
                }
            }
        }
        return bfs(start, end, g, m);

    }
}
