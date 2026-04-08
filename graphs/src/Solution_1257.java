import java.util.*;

public class Solution_1257 {

    class Solution {

        class Graph {

            private final Map<String, String> parent;

            public Graph() {
                this.parent = new HashMap<>();
            }

            void addEdge(String u, String v) {
                if (parent.containsKey(v)) {
                    System.out.println(u);
                }
                this.parent.put(v, u);
            }

            void parentChain(String u, Stack<String> stack) {
                stack.add(u);
                if (parent.containsKey(u)) {
                    parentChain(parent.get(u), stack);
                }
            }

            String lca(String r1, String r2) {
                Stack<String> pc1 = new Stack<>();
                parentChain(r1, pc1);
                Stack<String> pc2 = new Stack<>();
                parentChain(r1, pc2);
                String lastMatch = null;
                while (!pc1.isEmpty() && !pc2.isEmpty() && pc1.peek().equals(pc2.peek())) {
                    pc1.pop();
                    pc2.pop();
                }
                return lastMatch;
            }

        }

        public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
            Graph graph = new Graph();
            for (List<String> r : regions) {
                String u = r.getFirst();
                for (int i = 1; i < r.size(); i++) {
                    String v = r.get(i);
                    graph.addEdge(u, v);
                }
            }
            return graph.lca(region1, region2);
        }
    }
}
