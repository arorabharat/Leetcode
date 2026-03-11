import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

// TODO pending
class Solution_42 {


    // TODO: 11/03/26 - incorrect approach
    class Solution1 {

        //  [0,1,0,2,1,0,1,3,2,1,2,1]
        //  [0,1,0,2,1,0,1,3,2,1,2,1]
        // 4 2 1 3 4
        public int trap(int[] height) {
            Deque<int[]> decQ = new LinkedList<>();
            int n = height.length;
            int totalWater = 0;
            for (int i = 0; i < n; i++) {
                int h = height[i];
                if (decQ.isEmpty()) {
                    decQ.add(new int[]{i,h});
                } else {
                    int currentBucket = 0;
                    int count = 0;
                    int lastElementRemoved = h;
                    while (!decQ.isEmpty() && decQ.peekLast()[1] < h) {
                        int[] last = decQ.removeLast();
                        lastElementRemoved = last[1];
                        count = count + (i - last[0]);
                        currentBucket = currentBucket + lastElementRemoved;
                    }
                    if (count > 0) {

                        int leftHeight = (decQ.isEmpty()) ? lastElementRemoved : decQ.peekLast()[1];
                        totalWater = totalWater + Math.min(leftHeight, h) * count - currentBucket;
                        System.out.println(h + " " + currentBucket + " " + count + " " + leftHeight + " " + totalWater);

                    }
                    decQ.add(new int[]{i,h});
                }
            }
            return totalWater;
        }
    }


    public int trap(int[] height) {
        Stack<Pair> stack = new Stack<>();
        Pair zero = new Pair(0, -1);
        int length = height.length;
        int totalWater = 0;
        for (int i = 0; i < length; i++) {
            if (stack.isEmpty() || height[i] <= stack.peek().h) {
                stack.push(new Pair(i, height[i]));
            } else {
                Pair left = zero;
                int pillarSum = 0;
                while (!stack.isEmpty() && stack.peek().h < height[i]) {
                    Pair p = stack.pop();
                    pillarSum = pillarSum + p.h;
                    if (left.h < p.h) {
                        left = p;
                    }
                }
                if (!stack.isEmpty()) {
                    left = stack.peek();
                } else {
                    pillarSum = pillarSum - left.h;
                }
                int ht = Math.min(height[i], left.h);
                int wd = i - left.i - 1;
                int water = Math.max(ht * wd - pillarSum, 0);
                totalWater = totalWater + water;
            }
        }
        return totalWater;
    }

    static class Pair {
        int h;
        int i;

        Pair(int h, int i) {
            this.h = h;
            this.i = i;
        }
    }
}