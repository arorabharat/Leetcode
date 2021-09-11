import java.util.*;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/random-pick-index/
 */
public class Solution_398 {

    static class Solution {

        private final Map<Integer, List<Integer>> indicesList;
        private final Random random;

        public Solution(int[] nums) {
            int n = nums.length;
            indicesList = new HashMap<>();
            IntStream.range(0, n).forEach(i -> {
                if (!indicesList.containsKey(nums[i])) {
                    indicesList.put(nums[i], new ArrayList<>());
                }
                indicesList.get(nums[i]).add(i);
            });
            random = new Random();
        }

        public int pick(int target) {
            List<Integer> indices = indicesList.get(target);
            int m = indices.size();
            int randomNum = random.nextInt(m);
            return indices.get(randomNum);
        }
    }
}
