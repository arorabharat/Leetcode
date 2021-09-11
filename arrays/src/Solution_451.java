import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/sort-characters-by-frequency/
 * TODO try bucket sort next time
 */
class Solution_451 {
    public String frequencySort(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        int n = s.length();
        Character[] arr = new Character[n];
        IntStream.range(0, n).forEach(i -> {
            arr[i] = s.charAt(i);
        });
        Arrays.stream(arr).forEach(c -> {
            freq.put(c, freq.getOrDefault(c,0) + 1);
        });
        freq.forEach((k,v) -> freq.put(k, v * 1000 + k));
        Arrays.sort(arr, Comparator.comparingInt(c -> freq.get(c)).reversed());
        StringBuilder sb = new StringBuilder();
        Arrays.stream(arr).forEach(sb::append);
        return sb.toString();
    }
}

