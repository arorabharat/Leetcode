/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 */
class Solution_5 {


    class Approach_6 {
        // DP approach: O(n^2) time, O(n^2) space
        public String longestPalindrome(String s) {
            if (s == null || s.isEmpty()) return s;
            int n = s.length();
            boolean[][] dp = new boolean[n][n];

            int bestStart = 0;
            int bestLen = 1;

            // substrings of length 1 are palindromes
            for (int i = 0; i < n; i++) {
                dp[i][i] = true;
            }

            // consider substrings of length L from 2..n
            for (int L = 2; L <= n; L++) {
                for (int i = 0; i + L - 1 < n; i++) {
                    int j = i + L - 1;
                    // If ends match, check inner substring
                    if (s.charAt(i) == s.charAt(j)) {
                        // For length 2, if ends match, it's a palindrome (e.g., "aa")
                        // For length > 2, check if inner substring is palindrome
                        dp[i][j] = (L == 2) || dp[i + 1][j - 1];
                        
                        if (dp[i][j] && L > bestLen) {
                            bestLen = L;
                            bestStart = i;
                        }
                    }
                    // else dp[i][j] remains false (default value)
                }
            }

            return s.substring(bestStart, bestStart + bestLen);
        }
    }

    class Approach_1 {
        // Brute force approach: O(n^3) time, O(1) space
        // Optimized with early termination
        public String longestPalindrome(String s) {
            if (s == null || s.isEmpty()) {
                return "";
            }

            int n = s.length();
            int bestStart = 0;
            int bestLen = 1;
            
            for (int left = 0; left < n; left++) {
                // Early termination: if remaining length is less than bestLen, skip
                if (n - left <= bestLen) break;
                
                for (int right = left + bestLen; right < n; right++) {
                    // Only check if current substring could be longer
                    int currentLen = right - left + 1;
                    if (currentLen > bestLen && isPalindrome(s, left, right)) {
                        bestLen = currentLen;
                        bestStart = left;
                    }
                }
            }
            return s.substring(bestStart, bestStart + bestLen);
        }

        private boolean isPalindrome(String s, int left, int right) {
            while (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }

    // Using longest common substring with reversed string
    class Approach_2 {
        // The idea: longest palindrome is the longest common substring between s and reverse(s)
        // where the substring positions align correctly
        // Note: This approach is conceptually interesting but less efficient than direct DP
        // O(n^2) time, O(n^2) space
        
        public String longestPalindrome(String s) {
            if (s == null || s.isEmpty()) return "";
            int n = s.length();
            String reversed = new StringBuilder(s).reverse().toString();
            
            int[][] dp = new int[n + 1][n + 1];
            int maxLen = 0;
            int endIndexInS = 0;
            
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s.charAt(i - 1) == reversed.charAt(j - 1)) {
                        dp[i][j] = 1 + dp[i - 1][j - 1];
                        
                        // For a common substring to be a valid palindrome:
                        // If substring in s is from index (i-dp[i][j]) to (i-1) (0-based),
                        // and in reversed it's from (j-dp[i][j]) to (j-1) (0-based),
                        // then position (j-1) in reversed maps to (n-1-(j-1)) = (n-j) in original s (0-based)
                        // For palindrome: start in s should equal (n-1 - end in reversed)
                        // i - dp[i][j] = n - 1 - (j - 1)
                        // i - dp[i][j] = n - j
                        // i + j = n + dp[i][j]
                        // But we also need: end in s should equal (n-1 - start in reversed)
                        // i - 1 = n - 1 - (j - dp[i][j])
                        // i - 1 = n - j + dp[i][j] - 1
                        // i + j = n + dp[i][j]
                        // So the condition is: i + j = n + dp[i][j]
                        if (dp[i][j] > maxLen && (i + j == n + dp[i][j])) {
                            maxLen = dp[i][j];
                            endIndexInS = i;
                        }
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
            
            // Convert from 1-based to 0-based index
            if (maxLen == 0) {
                return s.substring(0, 1); // Return first character if no palindrome found
            }
            int start = endIndexInS - maxLen;
            return s.substring(start, endIndexInS);
        }
    }

    // Using memoized recursion (top-down DP)
    class Approach_3 {
        // O(n^2) time, O(n^2) space
        private Boolean[][] isPalindromeCache;
        private int bestStart = 0;
        private int bestLen = 1;

        private boolean isPalindrome(String s, int l, int r) {
            if (l >= r) return true;
            if (isPalindromeCache[l][r] != null) {
                return isPalindromeCache[l][r];
            }
            
            boolean result = (s.charAt(l) == s.charAt(r)) && isPalindrome(s, l + 1, r - 1);
            isPalindromeCache[l][r] = result;
            
            if (result) {
                int len = r - l + 1;
                if (len > bestLen) {
                    bestLen = len;
                    bestStart = l;
                }
            }
            
            return result;
        }

        public String longestPalindrome(String s) {
            if (s == null || s.isEmpty()) return "";
            int n = s.length();
            isPalindromeCache = new Boolean[n][n];
            bestStart = 0;
            bestLen = 1;
            
            // Check all possible substrings
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    isPalindrome(s, i, j);
                }
            }
            
            return s.substring(bestStart, bestStart + bestLen);
        }
    }
}

