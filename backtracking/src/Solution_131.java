import java.util.ArrayList;
import java.util.List;

public class Solution_131 {

    class Solution1 {

        List<List<String>> results = new ArrayList<>();

        boolean isPalindrome(String str, int s, int e) {
            while (s < e) {
                if (str.charAt(s) != str.charAt(e)) {
                    return false;
                }
                s++;
                e--;
            }
            return true;
        }

        private void _partition(String str, int s, int e, List<String> bucket) {
            if (s > e) {
                results.add(new ArrayList<>(bucket));
                return;
            }
            for (int i = s; i <= e; i++) {
                if (isPalindrome(str, s, i)) {
                    String subStr = str.substring(s, i + 1);
                    bucket.add(subStr);
                    _partition(str, i + 1, e, bucket);
                    bucket.removeLast();
                }
            }
        }

        public List<List<String>> partition(String s) {
            List<String> bucket = new ArrayList<>();
            _partition(s, 0, s.length() - 1, bucket);
            return results;
        }

    }
}
