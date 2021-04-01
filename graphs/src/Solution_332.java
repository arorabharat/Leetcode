import java.util.*;

/**
 * https://leetcode.com/problems/reconstruct-itinerary/
 */
public class Solution_332 {

    static class Graph {

        Map<String, List<String>> adj;

        Graph() {
            this.adj = new HashMap<>();
        }

        void addEdge(String u, String v) {
            if (!this.adj.containsKey(u)) {
                this.adj.put(u, new ArrayList<>());
            }
            if (!this.adj.containsKey(v)) {
                this.adj.put(v, new ArrayList<>());
            }
            this.adj.get(u).add(v);
        }

        Map<String, Integer> outDegree() {
            Map<String, Integer> map = new HashMap<>();
            for (String u : this.adj.keySet()) {
                for (String v : this.adj.get(u)) {
                    if (!map.containsKey(u)) {
                        map.put(u, 0);
                    }
                    if (!map.containsKey(v)) {
                        map.put(v, 0);
                    }
                    map.put(u, map.get(u) + 1);
                }
            }
            return map;
        }

        Map<String, Integer> inDegree() {
            Map<String, Integer> map = new HashMap<>();
            for (String u : this.adj.keySet()) {
                for (String v : this.adj.get(u)) {
                    if (!map.containsKey(u)) {
                        map.put(u, 0);
                    }
                    if (!map.containsKey(v)) {
                        map.put(v, 0);
                    }
                    map.put(v, map.get(v) + 1);
                }
            }
            return map;
        }

        boolean isEulerPathExist(Map<String, Integer> inDegreeMap, Map<String, Integer> outDegreeMap) {
            int startCount = 0;
            int endCount = 0;
            for (String node : outDegreeMap.keySet()) {
                if (Math.abs(outDegreeMap.get(node) - inDegreeMap.get(node)) > 1) {
                    return false;
                }
                if (outDegreeMap.get(node) - inDegreeMap.get(node) == 1) {
                    startCount++;
                }
                if (inDegreeMap.get(node) - outDegreeMap.get(node) == 1) {
                    endCount++;
                }
            }
            return startCount == 0 && endCount == 0 || startCount == 1 && endCount == 1;
        }

        Deque<String> deque;

        void dfs(String start, Map<String, Integer> outDegree) {
            while (outDegree.get(start) != 0) {
                outDegree.put(start, outDegree.get(start) - 1);
                dfs(this.adj.get(start).get(outDegree.get(start)), outDegree);
            }
            deque.addFirst(start);
        }

        /**
         * ============================= Euler's path =================================
         */
        List<String> eulerPath(String start) {
            Map<String, Integer> outDegreeMap = outDegree();
            Map<String, Integer> inDegreeMap = inDegree();
            if (!isEulerPathExist(inDegreeMap, outDegreeMap)) return new ArrayList<>();
            deque = new LinkedList<>();
            for (String u : this.adj.keySet()) {
                Collections.sort(this.adj.get(u));
                Collections.reverse(this.adj.get(u));
            }
            dfs(start, outDegreeMap);
            return new ArrayList<>(deque);
        }
    }

    public List<String> findItinerary(List<List<String>> tickets) {
        Graph graph = new Graph();
        for (List<String> ticket : tickets) {
            graph.addEdge(ticket.get(0), ticket.get(1));
        }
        return graph.eulerPath("JFK");
    }
}