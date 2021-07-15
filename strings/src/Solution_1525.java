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

    /**
     * Approach 2
     */
    public int numSplits1(String s) {
        int[] left = new int[CHARS];
        int[] right = new int[CHARS];
        int leftCount = 0;
        int rightCount = 0;
        for (char c : s.toCharArray()) {
            if (right[c - 'a'] == 0) rightCount++;
            right[c - 'a']++;
        }
        int goodSplit = 0;
        for (char c : s.toCharArray()) {

            if (left[c - 'a'] == 0) {
                leftCount++;
            }
            left[c - 'a']++;
            right[c - 'a']--;
            if (right[c - 'a'] == 0) {
                rightCount--;
            }
            if (leftCount == rightCount) goodSplit++;
        }
        return goodSplit;
    }
}