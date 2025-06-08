import java.util.Arrays;
import java.util.Comparator;

// TODO: 08/06/25 - This is pending.

public class Solution_3551 {
    class Solution {

        int sumOfDigits(int i) {
            int sum = 0;
            while (i > 0) {
                sum = sum + i % 10;
                i = i / 10;
            }
            return sum;
        }

        private Comparator<Pair> comparator() {
            return new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    int s1 = sumOfDigits(o1.value);
                    int s2 = sumOfDigits(o2.value);
                    int compare = Integer.compare(s1, s2);
                    if (compare == 0) {
                        return Integer.compare(o1.value, o2.value);
                    }
                    return compare;
                }
            };
        }

        public int minSwaps(int[] nums) {
            int length = nums.length;
            if (length < 2) return 0;
            Pair[] numsCopy = new Pair[length];
            for (int i = 0; i < length; i++) {
                numsCopy[i].value = nums[i];
                numsCopy[i].index = i;
            }
            Arrays.sort(numsCopy, comparator());
            int count = 0;
            for (int i = 0; i < length; i++) {
                int index = numsCopy[i].index;
                int value = numsCopy[i].value;
                if (nums[i] != value) {
                    int t = nums[i];
                    nums[i] = value;
                    nums[index] = t;
                    count++;
                }
            }
            return count;
        }

        static class Pair {
            int value;
            int index;

            public Pair(int value, int index) {
                this.value = value;
                this.index = index;
            }
        }
    }
}
