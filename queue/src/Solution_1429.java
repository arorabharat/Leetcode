import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Solution_1429 {

    class Approach_1 {

        class FirstUnique {

            private Queue<Integer> q = new LinkedList<>();
            private Set<Integer> us = new HashSet<>();
            private Set<Integer> ds = new HashSet<>();

            public FirstUnique(int[] nums) {
                for (int n : nums) {
                    if (!ds.contains(n)) {
                        if (us.contains(n)) {
                            ds.add(n);
                            us.add(n);
                        } else {
                            us.add(n);
                        }
                    }
                    q.add(n);
                }
            }

            public int showFirstUnique() {
                while (!q.isEmpty() && ds.contains(q.peek())) {
                    q.remove();
                }
                return (q.isEmpty()) ? -1 : q.peek();
            }

            public void add(int n) {
                q.add(n);
                if (!ds.contains(n)) {
                    if (us.contains(n)) {
                        ds.add(n);
                        us.add(n);
                    } else {
                        us.add(n);
                    }
                }
            }
        }

/**
 * Your FirstUnique object will be instantiated and called as such:
 * FirstUnique obj = new FirstUnique(nums);
 * int param_1 = obj.showFirstUnique();
 * obj.add(value);
 */
    }
}


