import java.util.HashSet;
import java.util.Set;

public class Solution_3 {

    class Solution1 {

        int windowSize = 1;

        void updateWindowSize(int startIndex, int endIndex) {
            windowSize = Math.max(windowSize, endIndex - startIndex + 1);
        }

        // constraints
        // startIndex - always be the starting index of window [0,N)
        // endIndex - always be the end index of window + 1 (0,N)
        public int lengthOfLongestSubstring(String s) {
            Set<Character> cache = new HashSet<>();
            int N = s.length();
            if (N == 0) return 0;
            int startIndex = 0;
            int endIndex = 0;
            char[] c = s.toCharArray();
            cache.add(c[startIndex]);
            updateWindowSize(startIndex, endIndex);
            while (endIndex < N) {
                char pushChar = c[endIndex];
                // try adding next char in window,
                // if char already exist then try to remove it first,
                while (startIndex < endIndex && cache.contains(pushChar)) {
                    char popChar = c[startIndex];
                    cache.remove(popChar);
                    startIndex++;
                }
                cache.add(pushChar);
                updateWindowSize(startIndex, endIndex);
                endIndex++;
            }
            return windowSize;
        }
    }

    class Solution2 {
        public int lengthOfLongestSubstring(String s) {
            int n = s.length();
            HashSet<Character> set = new HashSet<>();
            int left = 0, right = 0, maxLength = 0;

            while (right < n) {
                // Add character at 'right' to the set
                if (!set.contains(s.charAt(right))) {
                    set.add(s.charAt(right));
                    right++;  // Expand the window
                    maxLength = Math.max(maxLength, right - left);  // Update maxLength
                } else {
                    // If character is repeating, shrink window from left
                    set.remove(s.charAt(left));
                    left++;
                }
            }

            return maxLength;
        }
    }

}
