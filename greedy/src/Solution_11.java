class Solution_11 {

    class Solution {

        public int maxArea(int[] height) {
            int s = 0;
            int e = height.length - 1;
            int area = 0;
            int maxArea = 0;
            while (s < e) {
                if (height[s] < height[e]) {
                    area = (e - s) * height[s];
                    s++;
                } else if (height[s] > height[e]) {
                    area = (e - s) * height[e];
                    e--;
                } else {
                    area = (e - s) * height[s];
                    s++;
                }
                maxArea = Math.max(maxArea, area);
            }
            return maxArea;
        }
    }
}