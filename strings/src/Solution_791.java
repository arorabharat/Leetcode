import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * https://leetcode.com/problems/custom-sort-string/
 */
class Solution_791 {

    public String customSortString(String order, String s) {
        int n = 26;
        int[] arr = new int[n];
        int priority = 0;
        for (int i = 0; i < order.length(); i++) {
            if (arr[order.charAt(i) - 'a'] == 0) {
                priority++;
                arr[order.charAt(i) - 'a'] = priority;
            }
        }
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                priority++;
                arr[i] = priority;
            }
        }
        List<Character> chars = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            chars.add(s.charAt(i));
        }
        chars.sort(Comparator.comparingInt(c -> arr[c - 'a']));
        StringBuilder sb = new StringBuilder();
        for (Character character : chars) {
            sb.append(character);
        }
        return sb.toString();
    }
}