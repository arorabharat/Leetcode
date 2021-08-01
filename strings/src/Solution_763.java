import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/partition-labels/
 */
class Solution_763 {

    /**
     * Time Complexity :  O( N )
     * Space Complexity :  O( 1 )
     */
    public List<Integer> partitionLabels(String s) {
        int n = s.length();
        int M = 26;
        int[] lastIndex = new int[M];
        for (int i = 0; i < n; i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }
        int nextSplit = 0;
        int lastSplit = -1;
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int l = lastIndex[c - 'a'];
            nextSplit = Math.max(nextSplit, l);
            if (nextSplit == i) {
                result.add(nextSplit - lastSplit);
                lastSplit = nextSplit;
            }
        }
        return result;
    }
}
