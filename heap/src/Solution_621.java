import java.util.PriorityQueue;
import java.util.Stack;
import java.util.stream.IntStream;

class Solution_621 {

    public int leastInterval(char[] tasks, int n) {
        int[] count = new int[26];
        for (char c : tasks) {
            count[c - 'A']++;
        }
        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> (count[b] == count[a]) ? a - b : count[b] - count[a]);
        IntStream.range(0, 26).filter(e -> count[e] != 0).forEach(q::add);
        int time = 0;
        int len = tasks.length;
        while (len !=0 ) {
            int curr = q.remove();
            time++;
            count[curr]--;
            len--;
            if (len == 0) {
                break;
            }
            Stack<Integer> stack = new Stack<>();
            for (int j = 0; j < n; j++) {
                if (!q.isEmpty()) {
                    int next = q.remove();
                    stack.push(next);
                    count[next]--;
                    time++;
                    len--;
                    if (len == 0) break;
                } else {
                    time++;
                }
            }
            stack.stream().filter(e -> count[e] != 0).forEach(q::add);
            if (count[curr] != 0) {
                q.add(curr);
            }
        }
        return time;
    }
}