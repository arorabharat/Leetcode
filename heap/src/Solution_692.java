import java.util.*;

public class Solution_692 {

    class Approach_1 {

        record Stat(int count, String word) {
        }

        Map<String, Integer> stats = new HashMap<>();

        public List<String> topKFrequent(String[] words, int k) {
            for (String word : words) {
                stats.merge(word, 1, Integer::sum);
            }
            PriorityQueue<Stat> leaderBoard = new PriorityQueue<>(Comparator.comparingInt(Stat::count).thenComparing(Comparator.comparing(Stat::word).reversed()));
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                leaderBoard.add(new Stat(entry.getValue(), entry.getKey()));
                if (leaderBoard.size() > k) {
                    leaderBoard.remove();
                }
            }
            List<String> result = leaderBoard.stream().sorted(Comparator.comparingInt(Stat::count).reversed().thenComparing(Stat::word)).map(stat -> stat.word).toList();
            return result;
        }
    }
}
