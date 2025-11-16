import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 * TODO
 */
class Solution_84 {

    public int largestRectangleArea(int[] h) {
        int n = h.length;
        Deque<Integer> num = new LinkedList<>();
        Deque<Integer> count = new LinkedList<>();
        int ans = 0;
        for (int k : h) {
            if (num.isEmpty()) {
                ans = Math.max(ans, k);
                num.addLast(k);
                count.addLast(1);
            } else {
                int cnt = 1;
                while (!num.isEmpty() && num.peekLast() >= k) {
                    num.removeLast();
                    cnt = cnt + count.removeLast();
                }
                num.addLast(k);
                count.addLast(cnt);
                int size = num.size();
                int len = 0;
                for (int j = 0; j < size; j++) {
                    int nm = num.removeLast();
                    int ct = count.removeLast();
                    len = len + ct;
                    ans = Math.max(ans, len * nm);
                    num.addFirst(nm);
                    count.addFirst(ct);
                }
            }
        }
        return ans;
    }
}