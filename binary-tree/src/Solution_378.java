import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution_378 {

    //[ [1,5,9],
    //  [10,11,13],
    //  [12,13,15]],

    //[ [1,5,9],
    //  [10,4,13],
    //  [12,13,9]],

    //  k = 8


    class Solution1 {

        // max Heap approach
        public int kthSmallest(int[][] matrix, int k) {

            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            int R = matrix.length;
            int C = matrix.length;
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    int v = matrix[i][j];
                    maxHeap.add(v);
                    if (maxHeap.size() > k) {
                        maxHeap.poll();
                    }
                }
            }
            if (!maxHeap.isEmpty()) {
                return maxHeap.peek();
            } else {
                return Integer.MAX_VALUE;
            }
        }
    }

    class Solution2 {

        // min heap
        public int kthSmallest(int[][] matrix, int k) {
            int R = matrix.length;
            Comparator<int[]> firstIndexComparator = new Comparator<int[]>() {
                @Override
                public int compare(int[] c1, int[] c2) {
                    return Integer.compare(matrix[c1[0]][c1[1]], matrix[c2[0]][c2[1]]);
                }
            };
            PriorityQueue<int[]> q = new PriorityQueue<>(firstIndexComparator);
            for (int r = 0; r < R; r++) {
                q.add(new int[]{r, 0});
            }
            for (int i = 0; i < k - 1; i++) {
                int[] c = q.remove();
                if(c[1] + 1 < R) {
                    q.add(new int[]{c[0], c[1] + 1});
                }
            }
            if(q.isEmpty()) {
                return Integer.MIN_VALUE;
            } else {
                int[] c = q.peek();
                return matrix[c[0]][c[1]];
            }

        }

    }


}
