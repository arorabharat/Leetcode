import java.util.HashSet;
import java.util.Set;

public class Solution_898 {

    class Solution {

        private Set<Integer> globalSet;

        Set<Integer> bitwise(int[] arr, int s, int e) {
            if (s > e) {
                throw new RuntimeException("Invalid range");
            }
            if (s == e) {
                globalSet.add(arr[s]);
                return Set.of(arr[s]);
            }
            Set<Integer> subsetBitwise = bitwise(arr, s + 1, e);
            Set<Integer> setBitwise = new HashSet<>();
            for (int subsetNum : subsetBitwise) {
                int uniqueValue = subsetNum | arr[s];
                setBitwise.add(uniqueValue);
            }
            setBitwise.add(arr[s]);
            globalSet.addAll(setBitwise);
            return setBitwise;
        }

        public int subarrayBitwiseORs(int[] arr) {
            globalSet = new HashSet<>();
            bitwise(arr, 0, arr.length - 1);
            return globalSet.size();
        }
    }

    class Solution2 {

        public int subarrayBitwiseORs(int[] arr) {
            Set<Integer> allSubsetUniqueElementSet = new HashSet<>();
            Set<Integer> lastSubsetUniqueElementSet = new HashSet<>();
            for (int num : arr) {
                Set<Integer> currSubsetUniqueElementSet = new HashSet<>();
                for (Integer lastSubsetUniqueElement : lastSubsetUniqueElementSet) {
                    int currSubsetUniqueElement = num | lastSubsetUniqueElement;
                    currSubsetUniqueElementSet.add(currSubsetUniqueElement);
                }
                currSubsetUniqueElementSet.add(num);
                lastSubsetUniqueElementSet = currSubsetUniqueElementSet;
                allSubsetUniqueElementSet.addAll(lastSubsetUniqueElementSet);
            }
            return allSubsetUniqueElementSet.size();
        }
    }

}
