import java.util.Stack;

/**
 * https://leetcode.com/problems/shortest-common-supersequence/
 */
class Solution_1092 {

    public String shortestCommonSupersequence(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        // longest subsequence size dp array
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        int i = m;
        int j = n;
        // calculate common subsequence index in both string
        while (i >= 1 && j >= 1) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                stack1.push(i - 1);
                stack2.push(j - 1);
                i--;
                j--;
            } else {
                if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }

        i = 0;
        j = 0;
        // join the string
        StringBuilder strb = new StringBuilder();
        while (i < m && j < n) {
            // if there are no common element left break
            if (stack1.isEmpty() || stack2.isEmpty()) {
                break;
            }
            // element not part of common subsequence in string 1
            while (!stack1.isEmpty() && i != stack1.peek()) {
                strb.append(s1.charAt(i));
                i++;
            }
            // element not part of common subsequence in string 2
            while (!stack2.isEmpty() && j != stack2.peek()) {
                strb.append(s2.charAt(j));
                j++;
            }
            // append common char
            stack1.pop();
            stack2.pop();
            strb.append(s1.charAt(i));
            i++;
            j++;
        }
        // element left from both string after using common char
        strb.append(s1.substring(i));
        strb.append(s2.substring(j));
        return strb.toString();
    }
}