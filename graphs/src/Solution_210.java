import java.util.*;

/**
 * https://leetcode.com/problems/course-schedule-ii/
 * Find the topological sort
 */
class Solution_210 {

    Graph g;
    Deque<Integer> q;
    Set<Integer> visit;
    boolean[] stack;

    /**
     * ====================== topological sort ==============
     */
    void dfs(int u) {
        visit.add(u);
        for (int v : g.list(u)) {
            if (!visit.contains(v)) {
                dfs(v);
            }
        }
        q.addFirst(u);
    }

    boolean cycle(int u) {
        stack[u] = true;
        visit.add(u);
        for (int v : g.list(u)) {
            if (!visit.contains(v) && cycle(v)) {
                stack[u] = false;
                return true;
            } else if (visit.contains(v) && stack[v]) {
                stack[u] = false;
                return true;
            }
        }
        stack[u] = false;
        return false;
    }

    public int[] findOrder(int n, int[][] pre) {
        g = new Graph();
        for (int[] edge : pre) {
            g.addEdge(edge[1], edge[0]);
        }
        q = new LinkedList<>();
        visit = new HashSet<>();
        stack = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visit.contains(i)) {
                if (cycle(i)) {
                    return new int[0];
                }
            }
        }
        visit = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (!visit.contains(i)) {
                dfs(i);
            }
        }

        int[] ans = new int[q.size()];

        int i = 0;
        for (Integer x : q) {
            ans[i] = x;
            i++;
        }
        return ans;
    }

    /**
     * Create a directed graph , where a edge A -> B represent course B can be completed once course A is complete
     * We will also maintain the number of other course the every course is dependent on. From angle of graph that means in degree
     * We will add all the courses with zero pending dependency as they could be completed.
     * Once we complete a course, we iterate over all the course which were dependent on that course and reduce the numOfPending dependency count by 1.
     * If the completed courses equal to the total course then we have a order other wise there is a cycle and those courses could not be completed.
     */

    public int[] findOrder1(int numCourses, int[][] prerequisites) {
        int[] numOfPendingDependency = new int[numCourses];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] pair : prerequisites) {
            int prerequisite = pair[1];
            int course = pair[0];
            numOfPendingDependency[course]++;
            graph.get(prerequisite).add(course);
        }

        int completedCourse = 0;
        List<Integer> orderOfCourses = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (numOfPendingDependency[i] == 0) {
                completedCourse++;
                queue.add(i);
                orderOfCourses.add(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer course = queue.remove();
            for (int dependentCourse : graph.get(course)) {
                numOfPendingDependency[dependentCourse]--;
                if (numOfPendingDependency[dependentCourse] == 0) {
                    completedCourse++;
                    queue.add(dependentCourse);
                    orderOfCourses.add(dependentCourse);
                }
            }
        }
        if (completedCourse != numCourses) return new int[0];
        int[] ans = new int[orderOfCourses.size()];
        for (int i = 0; i < orderOfCourses.size(); i++) {
            ans[i] = orderOfCourses.get(i);
        }
        return ans;
    }

    static class Graph {
        Map<Integer, List<Integer>> adj = new HashMap<>();

        void addEdge(int u, int v) {
            if (!adj.containsKey(u)) {
                adj.put(u, new ArrayList<>());
            }
            adj.get(u).add(v);
        }

        List<Integer> list(int u) {
            if (adj.containsKey(u)) {
                return adj.get(u);
            }
            return new ArrayList<>();
        }

    }
}