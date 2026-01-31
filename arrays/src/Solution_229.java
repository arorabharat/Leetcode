import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

public class Solution_229 {

    class Solution1 {


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
