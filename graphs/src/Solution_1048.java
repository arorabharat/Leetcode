import java.util.ArrayList;
import java.util.List;

public class Solution_1048 {

    class Solution1 {

        boolean isPredecessor(String x, String y) {

            if (x.length() + 1 != y.length()) {
                return false;
            }

            int i = 0;
            int j = 0;
            int count = 0;

            while (i < x.length() && j < y.length()) {
                if (x.charAt(i) != y.charAt(j)) {
                    count++;
                    if (count > 1) {
                        return false;
                    }
                } else {
                    i++;
                }
                j++;
            }
            return count == 1 || (y.length() - j == 1);
        }


        class Graph {

            private final List<List<Integer>> adj;
            int[] indegree;

            Graph(int n) {
                adj = new ArrayList<>();
                indegree = new int[n];
                for (int i = 0; i < n; i++) {
                    adj.add(new ArrayList<>());
                }
            }

            void addEdge(int u, int v) {
                adj.get(u).add(v);
                indegree[v]++;
            }

            List<Integer> roots() {
                List<Integer> list = new ArrayList<>();
                for(int i  = 0 ; i < indegree.length ; i++) {
                    if(indegree[i] == 0) {

                    }
                }
            }

            int longestPath() {

            }
        }



        public int longestStrChain(String[] words) {
            int n = words.length;
            Graph g = new Graph(n);
            for (int u = 0; u < n; u++) {
                for (int v = u + 1; v < n; v++) {
                    if (isPredecessor(words[u], words[v])) {
                        g.addEdge(u, v);
                    }
                }
            }

        }

// a -> ba -> bca
// b -> ba -> bda -> bdca
    }
