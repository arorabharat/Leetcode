/**
 * https://leetcode.com/problems/number-of-good-ways-to-split-a-string/
 */
class Solution_1525 {

    private static final int CHARS = 26;

    boolean isCharCountMatch(int[] left, int[] right) {
        int leftCount = 0;
        for (int i = 0; i < CHARS; i++) {
            if (left[i] > 0) {
                leftCount++;
            }
        }
        int rightCount = 0;
        for (int i = 0; i < CHARS; i++) {
            if (right[i] > 0) {
                rightCount++;
            }
        }
        return leftCount == rightCount;
    }

    public int numSplits(String s) {
        int[] left = new int[CHARS];
        int[] right = new int[CHARS];
        for (char c : s.toCharArray()) {
            right[c - 'a']++;
        }
        int goodSplit = 0;
        for (char c : s.toCharArray()) {
            left[c - 'a']++;
            right[c - 'a']--;
            if (isCharCountMatch(left, right)) goodSplit++;
        }
        return goodSplit;
    }
}