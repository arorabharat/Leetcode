public class Solution_2133 {


    class Approach_1 {

        public boolean checkValid(int[][] matrix) {
            int n = matrix.length;

            if (n < 1) return true;
            int m = matrix[0].length;
            if (n != m) return false;
            for (int[] ints : matrix) {
                boolean[] v = new boolean[n];
                for (int j = 0; j < n; j++) {
                    if (ints[j] > n || ints[j] < 1) {
                        return false;
                    }
                    v[ints[j] - 1] = true;
                }
                for (boolean b : v) {
                    if (!b) {
                        return false;
                    }
                }
            }
            for (int j = 0; j < n; j++) {
                boolean[] v = new boolean[n];
                for (int[] ints : matrix) {
                    if (ints[j] > n || ints[j] < 1) {
                        return false;
                    }
                    v[ints[j] - 1] = true;
                }
                for (boolean b : v) {
                    if (!b) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    class Approach_2 {
        public boolean checkValid(int[][] matrix) {
            int n = matrix.length;
            if (n < 1) return true;
            int m = matrix[0].length;
            if (n != m) return false;
            for (int[] ints : matrix) {
                int xor = 1;
                for (int num : ints) {
                    xor = xor^num;
                }
                if (xor != 0) {
                        return false;
                }
            }
            for (int j = 0; j < n; j++) {
                boolean[] v = new boolean[n];
                for (int[] ints : matrix) {
                    if (ints[j] > n || ints[j] < 1) {
                        return false;
                    }
                    v[ints[j] - 1] = true;
                }
                for (boolean b : v) {
                    if (!b) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
