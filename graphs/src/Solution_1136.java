import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/parallel-courses/
 */
class Solution_1136 {


    /**
     * Create a directed graph , where a edge A -> B represent course B can be completed once course A is complete
     * We will also maintain the number of other course the every course is dependent on. From angle of graph that means in degree
     * We will add all the courses with zero pending dependency as they could be completed.
     * Once we complete a course, we iterate over all the course which were dependent on that course and reduce the numOfPending dependency count by 1.
     * We basically count the number of level in the BFS traversal of the graph, each level represent one sem
     */
    public int minimumSemesters(int n, int[][] relations) {
        int[] numOfPendingDependency = new int[n + 1];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] pair : relations) {
            int prerequisite = pair[0];
            int course = pair[1];
            numOfPendingDependency[course]++;
            graph.get(prerequisite).add(course);
        }

        int completedCourse = 0;
        int totalSemester = 0;
        int currentSemCourses = 0;
        int nextSemCourses = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (numOfPendingDependency[i] == 0) {
                currentSemCourses++;
                completedCourse++;
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            Integer course = queue.remove();
            currentSemCourses--;
            for (int dependentCourse : graph.get(course)) {
                numOfPendingDependency[dependentCourse]--;
                if (numOfPendingDependency[dependentCourse] == 0) {
                    completedCourse++;
                    nextSemCourses++;
                    queue.add(dependentCourse);
                }
            }
            if (currentSemCourses == 0) {
                currentSemCourses = nextSemCourses;
                nextSemCourses = 0;
                totalSemester++;
            }
        }
        if (completedCourse != n) return -1;
        return totalSemester;
    }
}
