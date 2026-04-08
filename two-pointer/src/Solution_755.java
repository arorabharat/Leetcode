public class Solution_755 {
    class Solution {

        int n;
        int k;

        boolean isValidR(int i) {
            return k < i && i < n;
        }

        boolean isValidL(int i) {
            return 0 <= i && i < k;
        }

        public int[] pourWater(int[] heights, int volume, int k) {
            int l = k - 1;
            int r = k + 1;
            this.n = heights.length;
            this.k = k;
            l = moveLLeft(heights, l);
            r = moveRRight(heights, r);
            for (int i = 0; i < volume; i++) {
                if (isValidL(l) && heights[l] < heights[k]) {
                    heights[l]++;
                    int nl = moveLLeft(heights, l);
                    if (nl != l && heights[l] == heights[nl]) {
                        nl = moveLRight(heights, l);
                    }
                    l = nl;
                } else if (isValidR(r) && heights[r] < heights[k]) {
                    heights[r]++;
                    int nr = moveRRight(heights, l);
                    if (nr != r && heights[nr] == heights[r]) {
                        nr = moveRLeft(heights, r);
                    }
                    r = nr;
                } else {
                    heights[k]++;
                }
            }
            return heights;
        }

        private int moveLLeft(int[] heights, int l) {
            while (isValidL(l - 1) && heights[l] >= heights[l - 1]) {
                l--;
            }
            return l;
        }

        private int moveLRight(int[] heights, int l) {
            while (isValidL(l + 1) && heights[l] >= heights[l + 1]) {
                l++;
            }
            return l;
        }

        private int moveRLeft(int[] heights, int r) {
            while (isValidR(r - 1) && heights[r] >= heights[r - 1]) {
                r--;
            }
            return r;
        }

        private int moveRRight(int[] heights, int r) {
            while (isValidR(r + 1) && heights[r] >= heights[r + 1]) {
                r++;
            }
            return r;
        }


    }


    class Solution2 {

        int n;
        int k;

        public int[] pourWater(int[] heights, int volume, int k) {

            this.n = heights.length;
            this.k = k;

            for (int v = 0; v < volume; v++) {

                int l = moveLLeft(heights, k);

                if (l < k && heights[l] < heights[k]) {
                    heights[l]++;
                    continue;
                }

                int r = moveRRight(heights, k);

                if (r > k && heights[r] < heights[k]) {
                    heights[r]++;
                    continue;
                }

                heights[k]++;
            }

            return heights;
        }

        private int moveLLeft(int[] h, int i) {
            int best = i;
            while (i > 0 && h[i-1] <= h[i]) {
                i--;
                if (h[i] < h[best]) best = i;
            }
            return best;
        }

        private int moveRRight(int[] h, int i) {
            int best = i;
            while (i < h.length-1 && h[i+1] <= h[i]) {
                i++;
                if (h[i] < h[best]) best = i;
            }
            return best;
        }
    }
}
