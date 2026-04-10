import java.util.*;

class KruskalMST {

    public int mst(int n, int[][] edges) {

        Arrays.sort(edges,
                Comparator.comparingInt(e -> e[2]));

        DSU dsu = new DSU(n);

        int cost = 0;

        for (int[] e : edges) {

            int u = e[0];
            int v = e[1];
            int w = e[2];

            if (dsu.union(u, v)) {
                cost += w;
            }
        }

        // check if graph connected
        if (dsu.getComponents() != 1) {
            return -1;
        }

        return cost;
    }
}