import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * https://leetcode.com/problems/shuffle-an-array/
 * TODO : do it yourself
 */
public class Solution_384 {

    static class Solution {
        private int[] array;
        private int[] original;
        private final Random rand = new Random();

        private List<Integer> getArrayCopy() {
            List<Integer> asList = new ArrayList<>();
            for (int j : array) {
                asList.add(j);
            }
            return asList;
        }

        public Solution(int[] nums) {
            array = nums;
            original = nums.clone();
        }

        public int[] reset() {
            array = original;
            original = original.clone();
            return array;
        }

        public int[] shuffle() {
            List<Integer> arrayList = getArrayCopy();
            for (int i = 0; i < array.length; i++) {
                int pickedNumber = rand.nextInt(arrayList.size());
                array[i] = arrayList.get(pickedNumber);
                arrayList.remove(pickedNumber);
            }
            return array;
        }
    }

}

