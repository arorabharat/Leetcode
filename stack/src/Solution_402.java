import java.util.*;

/**
 * https://leetcode.com/problems/remove-k-digits/
 */
class Solution_402 {

    class Solution1 {

        // 412361
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
                if (!sb.isEmpty() || c != '0') {
                    sb.append(c);
                }
            }
            return (sb.isEmpty()) ? "0" : sb.toString();
        }

    }

    class Solution2 {

        public String removeKdigits(String num, int k) {
            int n = num.length();
            if (k >= n) {
                return "0";
            }
            Comparator<int[]> pairComparator = new Comparator<int[]>() {
                @Override
                public int compare(int[] a, int[] b) {
                    if (a[0] == b[0]) {
                        return Integer.compare(a[1], b[1]);
                    }
                    return Integer.compare(a[0], b[0]);
                }
            };
            PriorityQueue<int[]> q = new PriorityQueue<>(pairComparator);
            StringBuilder result = new StringBuilder();
            int lastTopOptionIndex = -1;
            Set<Integer> removedIndex = new HashSet<>();
            for (int i = 0; i < k; i++) {
                q.add(new int[]{num.charAt(i) - '0', i});
            }
            for (int i = k; i < n; i++) {
                q.add(new int[]{num.charAt(i) - '0', i});
                while (!q.isEmpty() && removedIndex.contains(q.peek()[1])) {
                    q.remove();
                }
                int[] topOption = q.remove();
                if (result.isEmpty()) {
                    if (topOption[0] != 0) {
                        result.append(topOption[0]);
                    }
                } else {
                    result.append(topOption[0]);
                }
                for (int j = lastTopOptionIndex + 1; j < topOption[1]; j++) {
                    removedIndex.add(j);
                }
                lastTopOptionIndex = topOption[1];
            }
            if (result.isEmpty()) {
                return "0";
            } else {
                return result.toString();
            }
        }
    }
}
