import java.util.*;

public class Solution_373 {

    static class Pair {

        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair p = (Pair) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(x) + Integer.hashCode(y);
        }
    }


    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int m = nums2.length;
        List<List<Integer>> result = new ArrayList<>();
        Set<Pair> set = new HashSet<>();
        set.add(new Pair(0, 0));
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(index -> nums1[index[0]] + nums2[index[1]]));
        q.add(new int[]{0, 0});
        int count = 0;
        while (!q.isEmpty() && count < k) {
            List<Integer> pair = new ArrayList<>();
            int[] index = q.remove();
            pair.add(nums1[index[0]]);
            pair.add(nums2[index[1]]);
            result.add(pair);
            count++;
            int[] p1 = new int[]{index[0] + 1, index[1]};
            Pair pr1 = new Pair(index[0] + 1, index[1]);
            int[] p2 = new int[]{index[0], index[1] + 1};
            Pair pr2 = new Pair(index[0], index[1] + 1);
            int[] p3 = new int[]{index[0] + 1, index[1] + 1};
            Pair pr3 = new Pair(index[0] + 1, index[1] + 1);
            if (index[0] + 1 < n && !set.contains(pr1)) {
                q.add(p1);
                set.add(pr1);
            }
            if (index[1] + 1 < m && !set.contains(pr2)) {
                q.add(p2);
                set.add(pr2);
            }
            if (index[0] + 1 < n && index[1] + 1 < m && !set.contains(pr3)) {
                q.add(p3);
                set.add(pr3);
            }
        }
        return result;
    }
}
