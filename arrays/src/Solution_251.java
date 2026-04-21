public class Solution_251 {

    class Vector2D {

        int[][] vec;
        int[] it;
        int mit;
        int length;

        public Vector2D(int[][] vec) {
            this.vec = vec;
            this.length = vec.length;
            this.it = new int[length];
            this.mit = 0;
        }

        public int next() {
            if(hasNext()) {
                int i = it[mit];
                int v = vec[mit][i];
                it[mit]++;
                return v;
            }
            return -1;
        }

        public boolean hasNext() {
            while (mit < length) {
                int i = it[mit];
                if (i < vec[mit].length) {
                    return true;
                } else {
                    this.mit++;
                }
            }
            return false;
        }
    }
}
