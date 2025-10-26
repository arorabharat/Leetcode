import java.util.PriorityQueue;

public class Solution_215 {

    class Approach1 {

        public int findKthLargest(int[] nums, int k) {
            if (k == 0) return Integer.MIN_VALUE;
            int n = nums.length;
            if (n == 0 || k > n) return Integer.MIN_VALUE;
            PriorityQueue<Integer> q = new PriorityQueue<>();
            for (int c : nums) {
                q.add(c);
                if (q.size() > k) {
                    q.remove();
                }
            }
            return q.peek();
        }
    }

    class Approach2 {

        class Heap {

            private int c;
            private int s;
            private int[] a;

            Heap(int c) {
                this.c = c;
                a = new int[c + 1];
            }

            void swap(int i, int j) {
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }

            int peek() {
                return a[0];
            }

            void bubbleDown(int i) {
                int lc = 2 * i + 1;
                int rc = 2 * i + 2;
                int min = i;
                if (lc < s && a[lc] < a[min]) {
                    min = lc;
                }
                if (rc < s && a[rc] < a[min]) {
                    min = rc;
                }
                if (min != i) {
                    swap(min, i);
                    bubbleDown(min);
                }
            }

            void remove() {
                swap(0, s - 1);
                a[s - 1] = 0;
                s--;
                bubbleDown(0);
            }

            void bubbleUp(int i) {
                int p = (i - 1) / 2;
                if (p >= 0 && a[p] > a[i]) {
                    swap(p, i);
                    bubbleUp(p);
                }
            }

            void add(int v) {
                a[s] = v;
                bubbleUp(s);
                s++;
                if (s > c) {
                    remove();
                }
            }

        }

        public int findKthLargest(int[] nums, int k) {
            if (k == 0) return Integer.MIN_VALUE;
            int n = nums.length;
            if (n == 0 || k > n) return Integer.MIN_VALUE;
            Heap q = new Heap(k);
            for (int c : nums) {
                q.add(c);
            }
            return q.peek();
        }
    }
}
