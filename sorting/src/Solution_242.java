public class Solution_242 {
    class Solution {
        public boolean isAnagram(String s, String t) {
            int[] count = new int[26];
            for (char c : s.toLowerCase().toCharArray()) {
                count[c - 'a']++;
            }
            for (char c : t.toLowerCase().toCharArray()) {
                count[c - 'a']--;
            }
            for (int i = 0; i < 26; i++) {
                if (count[i] != 0) return false;
            }
            return true;
        }
    }
}
