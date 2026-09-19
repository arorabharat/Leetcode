import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Solution_843 {

    interface Master {
        int guess(String word);
    }

    class Solution1 {


        // O(N)
        private void _findSecretWord(List<Integer> indexes, String[] words, Master master, PriorityQueue<int[]> q) {
            if (q.isEmpty()) {
                return;
            }
            int[] curr = q.poll();
            String fw = words[curr[0]];
            int fwmc = master.guess(fw);
            if (fwmc == fw.length()) {
                return;
            }
            List<Integer> filter = new ArrayList<>();
            PriorityQueue<int[]> q2 = new PriorityQueue<>(Comparator.comparingInt(x -> x[1]));
            while (!q.isEmpty()) {
                int[] next = q.poll();
                int i = curr[0];
                int j = next[0];
                int zeroMatchCount = 0;
                int mc = matchCount(words[i], words[j]);
                if (mc == 0) {
                    zeroMatchCount++;
                }

            }
            q.add(new int[]{i, zeroMatchCount});
        }

        _findSecretWord(filter, words, master);
    }

    // O(1)
    int matchCount(String x, String y) {
        int count = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == y.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public void findSecretWord(String[] words, Master master) {
        int n = words.length;
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            indexes.add(i);
        }
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(x -> x[1]));
        for (int i = 0; i < n; i++) {
            int zeroMatchCount = 0;
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                int mc = matchCount(words[i], words[j]);
                if (mc == 0) {
                    zeroMatchCount++;
                }
            }
            q.add(new int[]{i, zeroMatchCount});
        }
        _findSecretWord(indexes, words, master, q);
    }
}
}
