import java.util.Arrays;

/**
 * https://leetcode.com/problems/assign-cookies/
 */
class Solution_455 {

    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int cookieIndex = 0;
        int content = 0;
        int kidIndex = 0;
        while (kidIndex < g.length) {
            if (cookieIndex == s.length) {
                break;
            } else if (s[cookieIndex] >= g[kidIndex]) {
                content++;
                kidIndex++;
                cookieIndex++;
            } else {
                cookieIndex++;
            }
        }
        return content;
    }
}