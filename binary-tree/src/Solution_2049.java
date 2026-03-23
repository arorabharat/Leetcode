import java.util.ArrayList;
import java.util.List;

public class Solution_2049 {

    class Solution {
        class Graph {

            int[] parents;
            int[] nodeCount;
            long[] scoreCount;
            int n;
            List<List<Integer>> adj;

            Graph(int[] parents) {
                this.n = parents.length;
                this.parents = parents;
                this.nodeCount = new int[n];
                this.scoreCount = new long[n];
                this.adj = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    this.adj.add(new ArrayList<>());
                }
                for (int i = 1; i < n; i++) {
                    int p = parents[i];
                    this.adj.get(i).add(p);
                    this.adj.get(p).add(i);
                }
            }

            void dfs0(int u, int p) {
                for (int v : this.adj.get(u)) {
                    if (v == p) {
                        continue;
                    }
                    dfs0(v, u);
                    nodeCount[u] += nodeCount[v];
                }
                nodeCount[u]++;
            }

            void dfs1(int u, int p) {
                scoreCount[u] = 1;
                for (int v : this.adj.get(u)) {
                    if (v == p) {
                        continue;
                    }
                    scoreCount[u] = scoreCount[u] * nodeCount[v];
                    dfs1(v, u);
                }
                if (this.n - nodeCount[u] != 0) {
                    scoreCount[u] = scoreCount[u] * (this.n - nodeCount[u]);
                }
            }

            int countScore() {
                dfs0(0, -1);
                dfs1(0, -1);
                long maxValue = Long.MIN_VALUE;
                for (int i = 0; i < n; i++) {
                    maxValue = Math.max(maxValue, scoreCount[i]);
                }
                int count = 0;
                for (int i = 0; i < n; i++) {
                    if (maxValue == scoreCount[i]) {
                        count++;
                    }
                }
                return count;
            }
        }

        public int countHighestScoreNodes(int[] parents) {
            Graph graph = new Graph(parents);
            return graph.countScore();
        }
    }

// LC * RC * (N - NC)
}
