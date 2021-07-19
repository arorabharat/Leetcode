import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/find-common-characters/
 */
class Solution_1002 {

    public List<String> commonChars(String[] words) {
        int n = words.length;
        int CHARS = 26;
        int[][] count = new int[n][CHARS];

        for (int i = 0; i < words.length; i++) {
            for (char c : words[i].toCharArray()) {
                count[i][c - 'a']++;
            }
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < CHARS; i++) {
            int min = count[0][i];
            for (int j = 0; j < n; j++) {
                min = Math.min(min, count[j][i]);
            }
            for (int j = 0; j < min; j++) {
                list.add(Character.toString('a' + i));
            }
        }
        return list;
    }
}