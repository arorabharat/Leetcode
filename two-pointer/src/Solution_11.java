/**
 * https://leetcode.com/problems/container-with-most-water/
 *
 * @see Solution_42
 */
class Solution_11 {
    /**
     * Take two pointers : i and j
     * initialise the maximum distance - maximise width
     * invariant - next width will always be less than previous width in our iteration.
     * So we need to maximise the height
     * We can increase the height at cost of decreasing the height.
     * So if the height of i is less than j than it will remain bottleneck for all the area so there is no point in keeping the pointer to i.
     * Similarly for j.
     * if height of both i and j is equal then we can move any of the pointer.
     * because all the smaller or equal pillar between these two equal height won't give us any height advantage so area will be always small.
     * and pillar would require other than these tow pillar to harvest height advantage.
     * Take maximum of all the possible area
     */
    public int maxArea(int[] height) {
        int n = height.length;
        int i = 0;
        int j = n - 1;
        int area = 0;
        while (i < j) {
            int tempArea;
            if (height[i] <= height[j]) {
                tempArea = height[i] * (j - i);
                i++;
            } else {
                tempArea = height[j] * (j - i);
                j--;
            }
            area = Math.max(area, tempArea);
        }
        return area;
    }
}