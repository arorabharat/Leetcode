import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/simplify-path/
 */
class Solution_71 {

    public String simplifyPath(String path) {
        Deque<String> q = new LinkedList<>();
        String[] arr = path.split("/");
        for (String e : arr) {
            if (e.equals("..")) {
                if (!q.isEmpty()) q.removeLast();
            } else if (!e.isBlank()) {
                q.addLast(e);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            sb.append("/");
            sb.append(q.removeFirst());
        }
        return (sb.length() == 0) ? "/" : sb.toString();
    }
}