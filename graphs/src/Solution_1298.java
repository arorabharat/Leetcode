import java.util.*;

public class Solution_1298 {

    class Solution {

        class Edge {

            int candies;
            int i;
            int[] keys;
            int[] boxes;

            public Edge(int candies, int i, int[] keys, int[] boxes) {
                this.candies = candies;
                this.i = i;
                this.keys = keys;
                this.boxes = boxes;
            }
        }

        public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
            int n = status.length;
            boolean[] discovered = new boolean[n];
            Queue<Edge> q = new LinkedList<>();
            boolean[] visited = new boolean[n];
            // O(N)
            for (int i : initialBoxes) {
                discovered[i] = true;
            }
            // O(N)
            for (int i = 0; i < discovered.length; i++) {
                if (discovered[i] && status[i] == 1) {
                    q.add(new Edge(candies[i], i, keys[i], containedBoxes[i]));
                    visited[i] = true;
                }
            }
            int totalCandies = 0;
            while (!q.isEmpty()) {
                Edge e = q.poll();
                totalCandies = totalCandies + e.candies;
                for (int d : e.boxes) {
                    if (visited[d]) {
                        continue;
                    }
                    if (!discovered[d] && status[d] == 1) {
                        q.add(new Edge(candies[d], d, keys[d], containedBoxes[d]));
                        visited[d] = true;
                    }
                    discovered[d] = true;
                }
                for (int k : e.keys) {
                    if (visited[k]) {
                        continue;
                    }
                    if (discovered[k] && status[k] == 0) {
                        q.add(new Edge(candies[k], k, keys[k], containedBoxes[k]));
                        visited[e.i] = true;
                    }
                    status[k] = 1;
                }
            }
            return totalCandies;
        }
    }


    // better and optimised
    class Solution2 {

        public int maxCandies(int[] status,
                              int[] candies,
                              int[][] keys,
                              int[][] containedBoxes,
                              int[] initialBoxes) {

            int n = status.length;

            boolean[] discovered = new boolean[n];
            boolean[] opened = new boolean[n];

            Queue<Integer> q = new LinkedList<>();

            // initial boxes
            for (int b : initialBoxes) {
                discovered[b] = true;
                if (status[b] == 1) {
                    q.add(b);
                    opened[b] = true;
                }
            }

            int total = 0;

            while (!q.isEmpty()) {

                int box = q.poll();
                total += candies[box];

                // discover new boxes
                for (int nb : containedBoxes[box]) {

                    if (!discovered[nb]) {
                        discovered[nb] = true;
                    }

                    if (status[nb] == 1 && !opened[nb]) {
                        q.add(nb);
                        opened[nb] = true;
                    }
                }

                // get new keys
                for (int key : keys[box]) {

                    status[key] = 1;

                    if (discovered[key] && !opened[key]) {
                        q.add(key);
                        opened[key] = true;
                    }
                }
            }

            return total;
        }
    }
}
