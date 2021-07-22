import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/single-number-ii/solution/
 * TODO: 22/07/21 bit manipulation
 */
class Solution_137 {

    /**
     * brute force approach using the map
     * Time Complexity :  O( N )
     * Space Complexity :  O( N )
     */
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int key : map.keySet()) {
            if (map.get(key) != 3) {
                return key;
            }
        }
        return Integer.MIN_VALUE;
    }
}
