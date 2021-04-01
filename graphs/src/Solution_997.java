import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/find-the-town-judge/
 */
class Solution_997 {
    public int findJudge(int N, int[][] trust) {
        int[] outDegree = new int[N + 1];
        int[] inDegree = new int[N + 1];
        IntStream.range(0, trust.length).forEach(index -> {
            outDegree[trust[index][0]]++;
            inDegree[trust[index][1]]++;
        });
        for (int i = 1; i <= N; i++) {
            if (outDegree[i] == 0 && inDegree[i] == N - 1) {
                return i;
            }
        }
        return -1;
    }

    public int findJudge1(int N, int[][] trust) {
        int[] outDegree = new int[N + 1];
        int[] inDegree = new int[N + 1];
        for (int[] edge : trust) {
            outDegree[edge[0]]++;
            inDegree[edge[1]]++;
        }
        for (int i = 1; i <= N; i++) {
            if (outDegree[i] == 0 && inDegree[i] == N - 1) {
                return i;
            }
        }
        return -1;
    }
}