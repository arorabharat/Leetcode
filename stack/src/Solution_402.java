import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/remove-k-digits/
 */
class Solution_402 {

    public String removeKdigits(String num, int k) {
        int n = num.length();
        Deque<Character> maxStack = new LinkedList<>();
        for (char c : num.toCharArray()) {
            while (!maxStack.isEmpty() && maxStack.getLast() > c && k > 0) {
                maxStack.removeLast();
                k--;
            }
            maxStack.addLast(c);
        }
        while (k > 0) {
            maxStack.removeLast();
            k--;
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : maxStack) {
            if (sb.length() != 0 || c != '0') {
                sb.append(c);
            }
        }
        return (sb.length() == 0) ? "0" : sb.toString();
    }
}
