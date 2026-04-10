import java.util.*;

class DSU {

    int[] parent;
    int[] rank;
    int components;

    DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    int find(int x) {

        if (parent[x] != x) {
            parent[x] = find(parent[x]);   // path compression
        }

        return parent[x];
    }

    boolean union(int a, int b) {

        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return false;
        }

        // union by rank
        if (rank[rootA] < rank[rootB]) {

            parent[rootA] = rootB;

        } else if (rank[rootA] > rank[rootB]) {

            parent[rootB] = rootA;

        } else {

            parent[rootB] = rootA;
            rank[rootA]++;
        }

        components--;

        return true;
    }

    boolean isConnected(int a, int b) {
        return find(a) == find(b);
    }

    int getComponents() {
        return components;
    }
}