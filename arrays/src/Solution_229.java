import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Solution_229 {

    class Solution {


        Map<Integer, Integer> count = new HashMap<>();

        public List<Integer> majorityElement(int[] nums) {
            IntConsumer intConsumer = new IntConsumer() {
                @Override
                public void accept(int x) {
                    count.put(x, count.getOrDefault(x, 0) + 1);
                }
            };
            Arrays.stream(nums).forEach(intConsumer);
            int t = nums.length / 3;
            Predicate<Integer> greaterThan = new Predicate<Integer>() {
                @Override
                public boolean test(Integer x) {
                    return x > t;
                }
            };
            return count.values().stream().filter(greaterThan).toList();
        }
    }
}
