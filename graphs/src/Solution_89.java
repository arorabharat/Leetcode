import java.util.ArrayList;
import java.util.List;

public class Solution_89 {

    List<Integer> seq;
    boolean[] visited;
    int count;
    int n;

    private boolean backTrack(int curr) {
        visited[curr] = true;
        seq.add(curr);
        if (seq.size() == count)
            return true;

        for (int j = 0; j < n; j++) {
            int next = curr ^ (1 << j);
            if (!visited[next] && backTrack(next)) {
                return true;
            }
        }
        visited[curr] = false;
        seq.removeLast();
        return false;
    }

    private static int flipJthBit(int i, int j) {
        int xorWith = ((int) Math.pow(2, j));
        return i ^ xorWith;
    }


    public List<Integer> grayCode(int n) {
        this.seq = new ArrayList<>();
        this.count = 1 << n;
        this.visited = new boolean[count];
        this.n = n;
        backTrack(0);
        return seq;
    }
}

/**
 * cycle covering all nodes
 * 000
 * 001      010    100
 * 011  101
 */


