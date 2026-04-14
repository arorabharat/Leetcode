import java.util.*;

class LongestPathDFSAlgo {

    public int longestPath(int n, List<List<Integer>> adj) {

        int[] dp = new int[n];
        boolean[] visited = new boolean[n];

        int ans = 0;

        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dfs(i, adj, dp));
        }

        return ans;
    }

    private int dfs(int u, List<List<Integer>> adj, int[] dp) {

        if (dp[u] != 0)
            return dp[u];

        int max = 0;

        for (int v : adj.get(u)) {
            max = Math.max(max, dfs(v, adj, dp));
        }

        return dp[u] = 1 + max;
    }
}
