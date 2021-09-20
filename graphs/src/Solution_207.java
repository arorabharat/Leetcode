import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/course-schedule/
 * <p>
 * Check if the graph is DAG / directed acyclic graph
 * is topological sort exist
 */
class Solution_207 {

    List<List<Integer>> g;
    boolean[] stack;
    boolean[] v;

    /**
     * @see DSA#CYCLE_DETECTION_IN_GRAPH
     */
    boolean cycle(int s) {
        stack[s] = true;
        v[s] = true;
        for (Integer c : g.get(s)) {
            if (!v[c] && cycle(c)) {
                return true;
            } else if (v[c] && stack[c]) {
                return true;
            }
        }
        stack[s] = false;
        return false;
    }

    /**
     * Stream api style , cycle detection
     */
    boolean cycle1(int s) {
        stack[s] = true;
        v[s] = true;
        boolean result = g.get(s).stream().anyMatch(c -> !v[c] && cycle1(c) || v[c] && stack[c]);
        stack[s] = false;
        return result;
    }

    public boolean canFinish(int n, int[][] pre) {
        stack = new boolean[n];
        v = new boolean[n];

        // create structure for the graph O(n)
        g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }

        // add edges o(m)
        for (int[] ints : pre) {
            g.get(ints[1]).add(ints[0]);
        }

        // check cycle on each edge o(n)
        for (int i = 0; i < n; i++) {
            if (!v[i] && cycle(i)) return false;
        }

        return true;
    }

    /**
     * @see DSA#KHANS_ALGO_OF_TOPOLOGICAL_SORT
     * <p>
     * Create a directed graph , where a edge A -> B represent course B can be completed once course A is complete
     * We will also maintain the number of other course the every course is dependent on. From angle of graph that means in degree
     * We will add all the courses with zero pending dependency as they could be completed.
     * Once we complete a course, we iterate over all the course which were dependent on that course and reduce the numOfPending dependency count by 1.
     */

    public boolean canFinish1(int numCourses, int[][] prerequisites) {
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
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (numOfPendingDependency[i] == 0) {
                completedCourse++;
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer course = queue.remove();
            for (int dependentCourse : graph.get(course)) {
                numOfPendingDependency[dependentCourse]--;
                if (numOfPendingDependency[dependentCourse] == 0) {
                    completedCourse++;
                    queue.add(dependentCourse);
                }
            }
        }
        return completedCourse == numCourses;
    }
}