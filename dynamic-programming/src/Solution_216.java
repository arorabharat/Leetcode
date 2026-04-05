import java.util.ArrayList;
import java.util.List;

public class Solution_216 {

    class Solution {

        List<List<Integer>> combinationsList;

        private void _combinationSum3(int k, int n, int e, List<Integer> combo) {
            if (k == 0) {
                if (n == 0) {
                    combinationsList.add(combo);
                } else {
                    return;
                }
            }
            if (e <= 0) {
                return;
            }
            if (n >= e) {
                List<Integer> newCombo = new ArrayList<>(combo);
                newCombo.add(e);
                _combinationSum3(k - 1, n - e, e - 1, newCombo);
            }
            _combinationSum3(k - 1, n, e - 1, combo);
        }

        public List<List<Integer>> combinationSum3(int k, int n) {
            // f(X numbers, target) = f(X - 1 numbers, k, target) U f(X - 1 numbers, k-1, target - arr[X - 1]) if  target >= arr[X];
            // fX numbers, target) = f(X -1 numbers, target) U f(X - 2 numbers, k -1 , target - arr[X -2])
            combinationsList = new ArrayList<>();
            _combinationSum3(k, n, 9, new ArrayList<>());
            return this.combinationsList;
        }
    }
}
