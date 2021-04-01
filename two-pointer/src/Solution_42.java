import java.util.Stack;
// TODO pending
class Solution_42 {

    static class Pair {
        int h;
        int i;

        Pair(int h, int i) {
            this.h = h;
            this.i = i;
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
                if(!stack.isEmpty()){
                    left = stack.peek();
                }
                else{
                    pillarSum = pillarSum -  left.h;
                }
                int ht = Math.min(height[i], left.h);
                int wd = i - left.i - 1;
                int water = Math.max(ht * wd - pillarSum, 0);
                totalWater = totalWater + water;
            }
        }
        return totalWater;
    }
}