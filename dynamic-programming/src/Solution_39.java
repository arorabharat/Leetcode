import java.util.*;

public class Solution_39 {

    class Solution {

        int[] candidates;

        private void _combinationSum(int target, List<Integer> combination, List<List<Integer>> combinationList, Set<String> uniqueSet) {
            if (target == 0) {
                Collections.sort(combination);
                if(!uniqueSet.contains(combination.toString())) {
                    combinationList.add(combination);
                }
                return;
            }
            for (int candidate : this.candidates) {
                if (candidate <= target) {
                    List<Integer> newCombination = new ArrayList<>(combination);
                    newCombination.add(candidate);
                    _combinationSum(target - candidate, newCombination, combinationList, uniqueSet);
                }
            }
        }

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            this.candidates = candidates;
            List<List<Integer>> combinationList = new ArrayList<>();
            _combinationSum(target, new ArrayList<>(), combinationList, new HashSet<>());
            return combinationList;
        }
    }
}
