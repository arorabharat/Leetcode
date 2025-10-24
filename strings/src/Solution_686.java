import java.util.HashSet;
import java.util.Set;

public class Solution_686 {

    class Approach_1 {

        // space complexity : O(m+n)
        // time complexity : O(m/n)
        public int repeatedStringMatch(String a, String b) {
            if (b.isEmpty()) {
                return 0;
            }
            String temp = a;
            int count = 1;
            // optimisation using char matching first
            Set<Character> charset = new HashSet<>();
            for (char c : a.toCharArray()) {
                charset.add(c);
            }
            for (char c : b.toCharArray()) {
                if (!(charset.contains(c))) {
                    return -1;
                }
            }
            while (temp.length() <= 2 * a.length() + b.length()) {
                if (temp.contains(b)) {
                    return count;
                }
                temp = temp + a;
                count++;
            }
            return -1;
        }
    }
}
