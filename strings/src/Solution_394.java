public class Solution_394 {

    class Solution {

        boolean isOpeningBracket(char c) {
            return c == '[';
        }

        boolean isClosingBracket(char c) {
            return c == ']';
        }

        int closingBracketIndex(String str, int s) {
            if (s >= str.length() || !isOpeningBracket(str.charAt(s))) {
                throw new RuntimeException("Invalid start index" + s);
            }
            int count = 0;
            for (int i = s; i < str.length(); i++) {
                if (isClosingBracket(str.charAt(i))) {
                    count--;
                } else if (isOpeningBracket(str.charAt(i))) {
                    count++;
                }
                if (count == 0) {
                    return i;
                }
            }
            return -1;
        }

        private StringBuilder _decodeString(String str, int s, int e) {
            StringBuilder sbr = new StringBuilder();
            for (int i = s; i <= e; i++) {
                int freq = 0;
                while (i <= e && Character.isDigit(str.charAt(i))) {
                    freq = freq * 10 + (str.charAt(i) - '0');
                    i++;
                }

                if (freq == 0) {
                    if (!Character.isAlphabetic(str.charAt(i))) {
                        throw new RuntimeException("Invalid string");
                    }
                    sbr.append(str.charAt(i));
                } else {
                    int openingIndex = i;
                    int closingIndex = closingBracketIndex(str, openingIndex);
                    StringBuilder subStringBuilder = _decodeString(str, openingIndex + 1, closingIndex - 1);
                    sbr.repeat(String.valueOf(subStringBuilder), Math.max(0, freq));
                    i = closingIndex;
                }
            }
            return sbr;
        }

        public String decodeString(String s) {
            return _decodeString(s, 0, s.length() - 1).toString();
        }
    }
}
