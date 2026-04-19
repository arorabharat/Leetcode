import java.util.*;

public class Solution_39 {

    class Solution {

        int[] candidates;

        private void _combinationSum(int target, List<Integer> combination, List<List<Integer>> combinationList, Set<String> uniqueSet) {
            if (target == 0) {
                Collections.sort(combination);
                if (!uniqueSet.contains(combination.toString())) {
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

    class Solution2 {

        List<List<Integer>> combinations;

        void dfs(int[] candidates, int target, List<Integer> selectedNumbers) {
            if (target < 0) {
            } else if (target == 0) {
                this.combinations.add(List.copyOf(selectedNumbers));
            } else {
                for (int i = 0; i < combinations.size(); i++) {
                    List<Integer> newSelectedNumbers = new ArrayList<>(selectedNumbers);
                    selectedNumbers.add(candidates[i]);
                    dfs(candidates, target - candidates[i], newSelectedNumbers);
                    newSelectedNumbers.removeLast();
                }
            }
        }

        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            this.combinations = new ArrayList<>();
            dfs(candidates, target, new ArrayList<>());
            return this.combinations;
        }
    }

    // if there are duplicates in the candidates
    class Solution3 {

        public List<List<Integer>> combinationSum(int[] candidates, int target) {

            Arrays.sort(candidates); // important

            List<List<Integer>> result = new ArrayList<>();

            dfs(0, candidates, target, new ArrayList<>(), result);

            return result;
        }

        private void dfs(int start,
                         int[] candidates,
                         int target,
                         List<Integer> curr,
                         List<List<Integer>> result) {

            if (target == 0) {
                result.add(new ArrayList<>(curr));
                return;
            }

            for (int i = start; i < candidates.length; i++) {

                // skip duplicates
                if (i > start && candidates[i] == candidates[i-1])
                    continue;

                if (candidates[i] > target)
                    break;

                curr.add(candidates[i]);

                // reuse allowed → i
                dfs(i, candidates, target - candidates[i], curr, result);

                curr.remove(curr.size() - 1);
            }
        }
    }
}
