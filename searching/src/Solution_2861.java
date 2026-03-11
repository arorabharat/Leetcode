import java.util.ArrayList;
import java.util.List;

public class Solution_2861 {

    // [[1,1,1],
    // 1*[[1,1,1], needed{1,1,1} - stock{0,0,0} = required{1,1,1}*cost{} = value < budget
    // 10^8*[[1,1,1], needed{1,1,1} - stock{0,0,0} = required{1,1,1}*cost{} = value < budge
    // Log n*kbudget

    class Solution {

        List<Long> deficitCount(List<Integer> composition, List<Integer> stock, long alloyCount) {
            int n = stock.size();
            List<Long> deficitList = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                deficitList.add(Math.max((composition.get(i).longValue() * alloyCount - stock.get(i)), 0));
            }
            return deficitList;
        }

        long costCalc(List<Long> deficitList, List<Integer> costList) {
            int n = costList.size();
            long cost = 0;
            for (int i = 0; i < n; i++) {
                cost = cost + (costList.get(i).longValue() * deficitList.get(i));
            }
            return cost;
        }


        public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> compositionList, List<Integer> stock, List<Integer> costList) {
            int maxCount = Integer.MIN_VALUE;
            for (List<Integer> composition : compositionList) {
                int s = 0;
                int e = 200000001;
                int ans = 0;
                while (s <= e) {
                    int m = s + (e - s) / 2;
                    List<Long> deficitList = deficitCount(composition, stock, m);
                    long cost = costCalc(deficitList, costList);
                    if (cost > budget) {
                        e = m - 1;
                    } else {
                        s = m + 1;
                        ans = m;
                    }
                }
                maxCount = Math.max(ans, maxCount);
            }
            return maxCount;
        }
    }


}
