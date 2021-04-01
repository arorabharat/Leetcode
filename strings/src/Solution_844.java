import java.util.Stack;

class Solution_844 {

    private String getFinalString(String keyPress) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < keyPress.length(); i++) {
            if (keyPress.charAt(i) == '#') {
                if (!stack.isEmpty()) stack.pop();
            } else {
                stack.push(keyPress.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    public boolean backspaceCompare(String s, String t) {
        return getFinalString(s).equals(getFinalString(t));
    }

    /**
     * Using two pointer approach.
     * we will initialise two pointer from end of each string and traverse in backward direction if the char is # then we will skip the next char.
     * once we reach our final char in both string then we compare and if they are not equal then we can not match those chars.
     */
    public boolean backspaceCompare2(String s, String t) {
        int sSkipCount = 0;
        int tSkipCount = 0;
        int si = s.length() - 1;
        int ti = t.length() - 1;
        char backspace = '#';
        while (si >= 0 && ti >= 0) {
            if (s.charAt(si) == backspace) {
                sSkipCount++;
                si--;
            } else if (t.charAt(ti) == backspace) {
                tSkipCount++;
                ti--;
            } else if (sSkipCount > 0) {
                sSkipCount--;
                si--;
            } else if (tSkipCount > 0) {
                tSkipCount--;
                ti--;
            } else if (s.charAt(si) != t.charAt(ti)) {
                return false;
            } else {
                si--;
                ti--;
            }
        }
        while (si >= 0) {
            if (s.charAt(si) == backspace) {
                sSkipCount++;
                si--;
            } else if (sSkipCount > 0) {
                sSkipCount--;
                si--;
            } else {
                return false;
            }
        }
        while (ti >= 0) {
            if (t.charAt(ti) == backspace) {
                tSkipCount++;
                ti--;
            } else if (tSkipCount > 0) {
                tSkipCount--;
                ti--;
            } else {
                return false;
            }
        }
        return true;
    }


}