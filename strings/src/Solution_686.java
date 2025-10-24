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

    class Approach_2 {

        public int repeatedStringMatch(String a, String b) {
            int m = a.length();
            int n = b.length();
            // minimum repetitions so that repeated a has length >= b.length
            int minRepeats = (n + m - 1) / m;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < minRepeats; i++) {
                sb.append(a);
            }
            // Try with minRepeats, and maybe minRepeats+1, minRepeats+2
            for (int extra = 0; extra < 3; extra++) {
                if (sb.toString().contains(b)) {
                    return minRepeats + extra;
                }
                sb.append(a);
            }
            return -1;
        }
    }

}
