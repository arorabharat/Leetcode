import java.util.ArrayList;
import java.util.List;

public class Solution_336 {

    // O(N^2*L) solution will give TLE
    class Solution {

        // there is way to optimize space consumption.
        // O(L)
        boolean isPalindrome(String w1, String w2) {
            String w = w1 + w2;
            int i = 0;
            int j = w.length() - 1;
            while (i < j) {
                if (w.charAt(i) != w.charAt(j)) {
                    return false;
                }
                i++;
                j--;
            }
            return true;
        }

        public List<List<Integer>> palindromePairs(String[] words) {
            int length = words.length;
            List<List<Integer>> pairList = new ArrayList<>();
            // O(N^2)
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    if (i != j && isPalindrome(words[i], words[j])) {
                        pairList.add(List.of(i, j));
                    }
                }
            }
            return pairList;
        }
    }
}
