/**
 * https://leetcode.com/problems/edit-distance/
 * 
 * Problem: Given two strings word1 and word2, return the minimum number of operations 
 * required to convert word1 to word2. You have the following three operations permitted:
 * 1. Insert a character
 * 2. Delete a character
 * 3. Replace a character
 * 
 * Time Complexity: O(m * n) where m and n are lengths of word1 and word2
 * Space Complexity: Varies by approach (O(m*n) to O(min(m,n)))
 */
class Solution_72 {

    /**
     * Approach 1: Standard 2D DP (Optimized)
     * Time: O(m * n)
     * Space: O(m * n)
     * 
     * Recurrence: dp[i][j] = min operations to convert word1[0..i-1] to word2[0..j-1]
     * - If chars match: dp[i][j] = dp[i-1][j-1]
     * - Else: dp[i][j] = 1 + min(insert, delete, replace)
     */
    class Approach_1 {

        public int minDistance(String word1, String word2) {
            int m = word1.length();
            int n = word2.length();
            int[][] dp = new int[m + 1][n + 1];
            
            // Base cases: converting empty string to word2 or word1 to empty string
            for (int i = 0; i <= m; i++) {
                dp[i][0] = i; // delete all chars from word1
            }
            for (int j = 0; j <= n; j++) {
                dp[0][j] = j; // insert all chars to word1
            }
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        // Characters match, no operation needed
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        // Try all three operations and take minimum
                        // dp[i-1][j]: delete from word1
                        // dp[i][j-1]: insert into word1
                        // dp[i-1][j-1]: replace in word1
                        dp[i][j] = 1 + Math.min(dp[i - 1][j], 
                                      Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    }
                }
            }
            return dp[m][n];
        }
    }

    /**
     * Approach 2: Standard 2D DP with Helper Function
     * Time: O(m * n)
     * Space: O(m * n)
     * 
     * Same as Approach 1 but with cleaner code using helper function
     */
    class Approach_2 {
        private int min(int... nums) {
            int min = Integer.MAX_VALUE;
            for (int num : nums) {
                min = Math.min(min, num);
            }
            return min;
        }

        public int minDistance(String word1, String word2) {
            int m = word1.length();
            int n = word2.length();
            int[][] dp = new int[m + 1][n + 1];
            
            // Initialize base cases
            for (int i = 0; i <= m; i++) {
                dp[i][0] = i;
            }
            for (int j = 0; j <= n; j++) {
                dp[0][j] = j;
            }
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = 1 + min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);
                    }
                }
            }
            return dp[m][n];
        }
    }

    /**
     * Approach 3: Space-Optimized DP (2 Rows)
     * Time: O(m * n)
     * Space: O(min(m, n)) - uses only 2 rows
     * 
     * Optimization: Since we only need previous row, we can use rolling array
     */
    class Approach_3 {
        public int minDistance(String word1, String word2) {
            int m = word1.length();
            int n = word2.length();
            
            // Use smaller dimension for space optimization
            if (m < n) {
                return minDistance(word2, word1); // swap to use smaller dimension
            }
            
            int[] prev = new int[n + 1];
            int[] curr = new int[n + 1];
            
            // Initialize first row (empty word1)
            for (int j = 0; j <= n; j++) {
                prev[j] = j;
            }
            
            for (int i = 1; i <= m; i++) {
                curr[0] = i; // first column (empty word2)
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        curr[j] = prev[j - 1];
                    } else {
                        curr[j] = 1 + Math.min(prev[j], 
                                      Math.min(curr[j - 1], prev[j - 1]));
                    }
                }
                // Swap arrays for next iteration
                int[] temp = prev;
                prev = curr;
                curr = temp;
            }
            return prev[n];
        }
    }

    /**
     * Approach 4: Top-Down Memoization (Recursive DP)
     * Time: O(m * n)
     * Space: O(m * n) for memoization + O(m + n) for recursion stack
     * 
     * More intuitive recursive approach with memoization
     */
    class Approach_4 {
        private int match(char[] s1, char[] s2, int i, int j, Integer[][] memo) {
            // Base cases
            if (i == s1.length) return s2.length - j; // insert remaining chars
            if (j == s2.length) return s1.length - i; // delete remaining chars
            
            if (memo[i][j] != null) return memo[i][j];
            
            if (s1[i] == s2[j]) {
                // Characters match, move both pointers
                memo[i][j] = match(s1, s2, i + 1, j + 1, memo);
            } else {
                // Try all three operations
                int insert = match(s1, s2, i, j + 1, memo);     // insert char at i
                int delete = match(s1, s2, i + 1, j, memo);     // delete char at i
                int replace = match(s1, s2, i + 1, j + 1, memo); // replace char at i
                memo[i][j] = 1 + Math.min(insert, Math.min(delete, replace));
            }
            
            return memo[i][j];
        }

        public int minDistance(String word1, String word2) {
            if (word1.isEmpty()) return word2.length();
            if (word2.isEmpty()) return word1.length();
            
            Integer[][] memo = new Integer[word1.length()][word2.length()];
            return match(word1.toCharArray(), word2.toCharArray(), 0, 0, memo);
        }
    }

    /**
     * Approach 5: Single Row Space Optimization
     * Time: O(m * n)
     * Space: O(min(m, n)) - uses only 1 row with careful state management
     * 
     * Further optimization: Use single row by storing diagonal value
     */
    class Approach_5 {
        public int minDistance(String word1, String word2) {
            int m = word1.length();
            int n = word2.length();
            
            // Use smaller dimension for space optimization
            if (m < n) {
                return minDistance(word2, word1);
            }
            
            int[] dp = new int[n + 1];
            
            // Initialize: converting empty word1 to word2
            for (int j = 0; j <= n; j++) {
                dp[j] = j;
            }
            
            for (int i = 1; i <= m; i++) {
                int prevDiagonal = dp[0]; // stores dp[i-1][j-1]
                dp[0] = i; // converting word1[0..i-1] to empty string
                
                for (int j = 1; j <= n; j++) {
                    int temp = dp[j]; // save current dp[j] before updating (will become prevDiagonal)
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[j] = prevDiagonal;
                    } else {
                        // dp[j] = dp[i-1][j] (delete)
                        // dp[j-1] = dp[i][j-1] (insert)
                        // prevDiagonal = dp[i-1][j-1] (replace)
                        dp[j] = 1 + Math.min(dp[j], Math.min(dp[j - 1], prevDiagonal));
                    }
                    prevDiagonal = temp; // update for next iteration
                }
            }
            return dp[n];
        }
    }

    /**
     * Approach 6: Optimized with Common Prefix/Suffix Removal
     * Time: O(m * n) in worst case, but faster in practice
     * Space: O(m * n)
     * 
     * Optimization: Remove common prefix and suffix to reduce problem size
     */
    class Approach_6 {
        public int minDistance(String word1, String word2) {
            // Remove common prefix
            int prefixLen = 0;
            int minLen = Math.min(word1.length(), word2.length());
            while (prefixLen < minLen && word1.charAt(prefixLen) == word2.charAt(prefixLen)) {
                prefixLen++;
            }
            
            // Remove common suffix
            int suffixLen = 0;
            int i = word1.length() - 1;
            int j = word2.length() - 1;
            while (i >= prefixLen && j >= prefixLen && word1.charAt(i) == word2.charAt(j)) {
                suffixLen++;
                i--;
                j--;
            }
            
            // Work with reduced strings
            String s1 = word1.substring(prefixLen, word1.length() - suffixLen);
            String s2 = word2.substring(prefixLen, word2.length() - suffixLen);
            
            // Apply standard DP on reduced strings
            int m = s1.length();
            int n = s2.length();
            
            if (m == 0) return n;
            if (n == 0) return m;
            
            int[][] dp = new int[m + 1][n + 1];
            
            for (int k = 0; k <= m; k++) {
                dp[k][0] = k;
            }
            for (int k = 0; k <= n; k++) {
                dp[0][k] = k;
            }
            
            for (int k = 1; k <= m; k++) {
                for (int l = 1; l <= n; l++) {
                    if (s1.charAt(k - 1) == s2.charAt(l - 1)) {
                        dp[k][l] = dp[k - 1][l - 1];
                    } else {
                        dp[k][l] = 1 + Math.min(dp[k - 1][l], 
                                      Math.min(dp[k][l - 1], dp[k - 1][l - 1]));
                    }
                }
            }
            return dp[m][n];
        }
    }

    /**
     * Approach 7: Recursive without Memoization (Brute Force - for comparison)
     * Time: O(3^(m+n)) - exponential, very slow
     * Space: O(m + n) - recursion stack only
     * 
     * This approach is included for educational purposes to show the naive solution
     * DO NOT use in production - it's too slow for large inputs
     */
    class Approach_7 {
        public int minDistance(String word1, String word2) {
            return helper(word1, word2, 0, 0);
        }
        
        private int helper(String word1, String word2, int i, int j) {
            // Base cases
            if (i == word1.length()) return word2.length() - j;
            if (j == word2.length()) return word1.length() - i;
            
            if (word1.charAt(i) == word2.charAt(j)) {
                return helper(word1, word2, i + 1, j + 1);
            } else {
                int insert = helper(word1, word2, i, j + 1);
                int delete = helper(word1, word2, i + 1, j);
                int replace = helper(word1, word2, i + 1, j + 1);
                return 1 + Math.min(insert, Math.min(delete, replace));
            }
        }
    }

    /**
     * Approach 8: Iterative with Early Termination Optimization
     * Time: O(m * n) in worst case, but can be faster with early termination
     * Space: O(min(m, n))
     * 
     * Uses single row optimization with potential for early termination
     * when strings are very different
     */
    class Approach_8 {
        public int minDistance(String word1, String word2) {
            int m = word1.length();
            int n = word2.length();
            
            if (m < n) {
                return minDistance(word2, word1);
            }
            
            // Early termination: if one string is empty
            if (n == 0) return m;
            if (m == 0) return n;
            
            int[] dp = new int[n + 1];
            
            // Initialize
            for (int j = 0; j <= n; j++) {
                dp[j] = j;
            }
            
            for (int i = 1; i <= m; i++) {
                int prevDiagonal = dp[0];
                dp[0] = i;
                
                for (int j = 1; j <= n; j++) {
                    int temp = dp[j];
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[j] = prevDiagonal;
                    } else {
                        dp[j] = 1 + Math.min(dp[j], Math.min(dp[j - 1], prevDiagonal));
                    }
                    prevDiagonal = temp;
                }
            }
            
            return dp[n];
        }
    }
}