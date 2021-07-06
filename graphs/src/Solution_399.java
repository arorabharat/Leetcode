import java.util.*;

/**
 * https://leetcode.com/problems/evaluate-division/
 */
class Solution_399 {

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Graph graph = new Graph();
        int n = values.length;
        for (int i = 0; i < n; i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            double w = values[i];
            graph.addEdge(a, b, w);
        }
        int m = queries.size();
        double[] ans = new double[m];
        for (int i = 0; i < m; i++) {
            String target = queries.get(i).get(0);
            String start = queries.get(i).get(1);
            ans[i] = graph.query(target, start);
        }
        return ans;
    }

    static class Pair {
        String nodeValue;
        double edgeValue;

        public Pair(String nodeValue, double edgeValue) {
            this.nodeValue = nodeValue;
            this.edgeValue = edgeValue;
        }
    }

    static class Graph {

        private final Map<String, List<Pair>> adj;
        Stack<Pair> currPath;
        Stack<Pair> reqPath;
        private Set<String> recurStack;


        Graph() {
            this.adj = new HashMap<>();
        }

        public void addEdge(String a, String b, double w) {
            if (!this.adj.containsKey(a)) {
                this.adj.put(a, new ArrayList<>());
            }
            if (!this.adj.containsKey(b)) {
                this.adj.put(b, new ArrayList<>());
            }
            this.adj.get(a).add(new Pair(b, 1 / w));
            this.adj.get(b).add(new Pair(a, w));
        }

        private boolean dfs(String curr, String target) {
            if (curr.equals(target)) {
                this.reqPath.addAll(this.currPath);
                return true;
            }
            if (recurStack.contains(curr)) {
                return false;
            }
            recurStack.add(curr);
            for (Pair edge : this.adj.get(curr)) {
                this.currPath.add(edge);
                if (dfs(edge.nodeValue, target)) {
                    this.currPath.pop();
                    this.recurStack.remove(curr);
                    return true;
                }
                this.currPath.pop();
            }
            recurStack.remove(curr);
            return false;
        }

        public double query(String target, String start) {
            if (this.adj.containsKey(target) && this.adj.containsKey(start)) {
                this.recurStack = new HashSet<>();
                this.currPath = new Stack<>();
                this.reqPath = new Stack<>();
                if (dfs(start, target)) {
                    double ans = 1;
                    while (!this.reqPath.isEmpty()) {
                        Pair edge = this.reqPath.pop();
                        ans = ans * edge.edgeValue;
                    }
                    return ans;
                }
            }
            return -1;
        }
    }
}