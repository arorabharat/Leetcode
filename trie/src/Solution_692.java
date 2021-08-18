import java.util.*;

/**
 * https://leetcode.com/problems/top-k-frequent-words/
 */
class Solution_692 {

    public List<String> topKFrequent(String[] words, int k) {

        Map<String, Integer> count = new HashMap<>();
        for (String word : words) {
            count.put(word, count.getOrDefault(word, 0) + 1);
        }
        PriorityQueue<String> maxHeap = new PriorityQueue<>(
                (str1, str2) -> {
                    if (count.get(str1).equals(count.get(str2))) {
                        return str1.compareTo(str2);
                    } else {
                        return -Integer.compare(count.get(str1), count.get(str2));
                    }
                });
        maxHeap.addAll(count.keySet());
        List<String> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(maxHeap.remove());
        }
        return result;
    }
}