import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

class Solution {

    public int[] finalPrices(int[] prices) {
        Deque<Integer> incQ = new LinkedList<>();
        int n = prices.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = prices[i];
        }
        for (int j = 0; j < n; j++) {
            int i = getLastIndex(incQ);
            while (i != -1 && prices[j] <= prices[i]) {
                res[i] = prices[i] - prices[j];
                incQ.removeLast();
                i = getLastIndex(incQ);
            }
            incQ.add(j);
        }
        return res;
    }

    private static int getLastIndex(Deque<Integer> incQ) {
        return incQ.isEmpty() ? -1 : incQ.getLast();
    }
}