import java.util.Stack;

class Solution_316 {

    private static final int NUM_OF_LETTERS = 26;

    private int toInt(char c) {
        return c - 'a';
    }

    /**
     * We maintain number of occurrence in right from current index for each character.
     * We initialise the array total number of count each character occur.
     * Than we iterate over each character and add it to the final string if it is not been already added.
     * When we encounter a char which smaller than the last we added we remove (included = false) the last character if it occurs again later ( right occurrence > 0 )
     */
    public String removeDuplicateLetters(String s) {
        int[] numOfOccurrenceInRight = new int[NUM_OF_LETTERS];
        char[] c = s.toCharArray();
        for (char ch : c) {
            numOfOccurrenceInRight[toInt(ch)]++;
        }
        boolean[] included = new boolean[NUM_OF_LETTERS];
        Stack<Character> st = new Stack<>();
        for (char ch : c) {
            numOfOccurrenceInRight[toInt(ch)]--;
            if (!included[toInt(ch)]) {
                while (!st.isEmpty() && ch < st.peek() && numOfOccurrenceInRight[toInt(st.peek())] > 0) {
                    included[toInt(st.pop())] = false;
                }
                st.push(ch);
                included[toInt(ch)] = true;
            }
        }
        StringBuilder ans = new StringBuilder();
        while (!st.isEmpty()) {
            ans.append(st.pop());
        }
        return ans.reverse().toString();
    }
}