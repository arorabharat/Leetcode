import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/minimum-height-trees/
 */
class Solution_310 {

    static class Graph {

        Map<Integer, List<Integer>> adj;

        int n;

        Graph(int n) {
            this.n = n;
            adj = new HashMap<>();
            for (int i = 0; i < n; i++) {
                adj.put(i, new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int min_element(int[] a) {
            int m = a.length;
            int index = 0;
            for (int i = 0; i < m; i++) {
                if (a[index] > a[i]) {
                    index = i;
                }
            }
            return a[index];
        }

        int max_index(int[] a) {
            int m = a.length;
            int index = 0;
            for (int i = 0; i < m; i++) {
                if (a[index] < a[i]) {
                    index = i;
                }
            }
            return index;
        }


        List<Integer> minHeight() {
            List<Integer> ans = new ArrayList<>();
            int[] h = new int[n];
            height(0, -1, 0, h);
            int index = max_index(h);

            int[] h1 = new int[n];
            height(index, -1, 0, h1);
            index = max_index(h1);
            int[] h2 = new int[n];
            height(index, -1, 0, h2);

            for (int i = 0; i < n; i++) {
                h[i] = Math.max(h1[i], h2[i]);
            }

            int min_height = min_element(h);

            for (int i = 0; i < n; i++) {
                if (min_height == h[i]) {
                    ans.add(i);
                }
            }
            return ans;
        }

        void height(int u, int p, int dis, int[] h) {
            for (int v : adj.get(u)) {
                if (v != p) {
                    height(v, u, dis + 1, h);
                }
            }
            h[u] = dis;
        }
    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        Graph g = new Graph(n);
        int m = edges.length;

        for (int[] edge : edges) {
            g.addEdge(edge[0], edge[1]);
        }

        return g.minHeight();
    }
}