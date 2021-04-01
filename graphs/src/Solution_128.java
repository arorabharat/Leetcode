import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://leetcode.com/problems/longest-consecutive-sequence/
 */
class Solution_128 {

    static class Graph {

        Map<Integer, List<Integer>> adj;
        Set<Integer> visited;

        Graph() {
            adj = new HashMap<>();
        }

        boolean addEdge(int u, int v) {
            if (!doesVertexExists(u) || !doesVertexExists(v)) {
                return false;
            }
            adj.get(u).add(v);
            adj.get(v).add(u);
            return true;
        }

        private boolean doesVertexExists(int u) {
            return this.adj.containsKey(u);
        }

        void addVertexIfNotExist(int u) {
            if (!doesVertexExists(u)) {
                adj.put(u, new ArrayList<>());
            }
        }

        int maxSizeComponent() {
            visited = new HashSet<>();
            AtomicInteger count = new AtomicInteger(Integer.MIN_VALUE);
            adj.forEach((vertex, adjList) -> {
                if (!visited.contains(vertex)) {
                    int numOfNodes = bfs(vertex);
                    count.set(Math.max(count.get(), numOfNodes));
                }
            });
            return count.get();
        }

        int bfs(Integer start) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);
            visited.add(start);
            AtomicInteger count = new AtomicInteger(1);
            while (!queue.isEmpty()) {
                Integer curr = queue.remove();
                adj.get(curr).forEach((next) -> {
                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.add(next);
                        count.getAndIncrement();
                    }
                });
            }
            return count.get();
        }
    }

    public int longestConsecutive(int[] nums) {
        Graph graph = new Graph();
        for (int num : nums) {
            graph.addVertexIfNotExist(num);
            graph.addEdge(num, num - 1);
            graph.addEdge(num, num + 1);
        }
        return graph.maxSizeComponent();
    }

    public int longestConsecutive1(int[] nums) {
        Set<Integer> num_set = new HashSet<>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }
}
