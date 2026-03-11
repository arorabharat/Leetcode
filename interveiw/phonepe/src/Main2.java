import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  <p>
 *  PhonePe has a DC of n servers [numbered from 0 to n-1], wherein some or all of the servers are connected together via 'c' cables.
 *  <p>
 *  n = 6, connections = {{0,1},{0,2},{0,3},{1,2},{1,3},{2,3}}
 *  <p>
 *  3
 *  /  |  \
 *  |  |    \
 *  |  |      \
 *  |  0-------1
 *  |  |       /
 *  |  |     /
 *  \  |   /
 *  2
 *  <p>
 *  4  5
 *  <p>
 *  N - connected components
 *  N - 1 required edges
 *  <p>
 *  Number of edges of available - Disjoint Set ( union find)
 *  <p>
 *  1. ALl Nodes components inttsefl
 *  2. Iterate through edges ( try add edge one a at time) [union]  edge - two nodes
 *  2.a.  node - if it making cycle (union and find) if yes - edge is available for me to use at other location (store edges List)
 *  Size List - available of edges for disconnecting and connecting
 *  Size of connected components - How edges I need to connect ?
 *  (available)5 - 2 (need)- any top 3
 *  <p>
 *  C*N*(path comparession factor)
 *  <p>
 *  // representative
 *  SC - O(N + E)
 *  <p>
 *  <p>
 *  Find the min sized list of cables from the given set of connections that can be removed and placed between
 *  other servers in order to connect all of the servers together.. (Note: there can be multiple answers)
 *  <p>
 *  {{1,3}, {1,2}} OR
 *  {{0,2}, {0,3}} ...
 *  <p>
 *  - if all servers are already connected, you can return an empty list..
 *  - if there are not not enough cables, you can throw an exception..
 *  <p>
 *  public List<int[]> makeConnected(int n, int[][] connections) {
 *  <p>
 *  }
 */

class Main2 {


    static class DSU {

        int[] rep;

        DSU(int n) {
            rep = new int[n];
            for (int i = 0; i < n; i++) {
                rep[i] = i;
            }
        }

        // 0 -> 1 -> 2
        // 0 -> 1
        // 0 -> 2
        int find(int x) {
            if (rep[x] != x) {
                rep[x] = find(rep[x]);
            }
            return rep[x];
        }

        boolean union(int x, int y) {
            int rx = find(x);
            int ry = find(y);
            if (rx == ry) {
                return true;
            }
            rep[rx] = ry;
            return false;
        }

        int connectComponents() {
            Set<Integer> repSet = new HashSet<>();
            for (int r : this.rep) {
                repSet.add(r);
            }
            return repSet.size();
        }


    }

    static public List<int[]> makeConnected(int n, int[][] connections) {

        DSU dsu = new DSU(n);

        List<int[]> availableEdge = new ArrayList<>();
        for (int[] c : connections) {
            if (dsu.union(c[0], c[1])) {
                availableEdge.add(c);
            }
        }
        int requiredEdge = dsu.connectComponents() - 1;
        List<int[]> result = new ArrayList<>();
        if (requiredEdge == 0) {
            return new ArrayList<>();
        } else {
            if (requiredEdge > availableEdge.size()) {
                throw new RuntimeException("Not enough edge");
            }
            for (int i = 0; i < requiredEdge; i++) {
                result.add(availableEdge.get(i));
            }
            return result;
        }

    }

    public static void main(String args[]) throws Exception {

    }
}