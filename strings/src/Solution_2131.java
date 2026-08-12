import java.util.HashMap;
import java.util.Map;

public class Solution_2131 {

    private String getReverse(String word) {
        return new StringBuilder(word).reverse().toString();
    }

    public int longestPalindrome(String[] words) {
        Map<String, Integer> unmatched = new HashMap<>();
        int len = 0;
        for (String word : words) {
            String rev = getReverse(word);
            int count = unmatched.getOrDefault(rev, 0);
            if (count > 0) {
                count--;
                if (count > 0) {
                    unmatched.put(rev, count);
                } else {
                    unmatched.remove(rev);
                }
                len = len + 4;
            } else {
                unmatched.put(word, unmatched.getOrDefault(word, 0) + 1);
                ;
            }
        }
        for (String word : unmatched.keySet()) {
            String rev = getReverse(word);
            int count = unmatched.getOrDefault(rev, 0);
            if (count > 0) {
                len = len + 2;
                break;
            }
        }
        return len;
    }
}
