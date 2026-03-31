
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode.com/problems/subsets/
 */
class Solution_78 {

    private List<List<Integer>> _subsets(int[] nums, int n) {
        if (n < 0) {
            List<List<Integer>> emptySet = new ArrayList<>();
            emptySet.add(new ArrayList<>());
            return emptySet;
        }
        List<List<Integer>> sets = _subsets(nums, n - 1);
        List<List<Integer>> result = new ArrayList<>(sets);
        for (List<Integer> set : sets) {
            List<Integer> copy = new ArrayList<>(set);
            copy.add(nums[n]);
            result.add(copy);
        }
        return result;
    }

    public List<List<Integer>> subsets(int[] nums) {
        return _subsets(nums, nums.length - 1);
    }


    class Approach2 {

        class Solution {

            public List<List<Integer>> _subsets(int[] nums, int end) {
                List<List<Integer>> subsetList = new ArrayList<>();
                if (end == 0) {
                    subsetList.add(new ArrayList<>());
                    List<Integer> singleElement = new ArrayList<>();
                    singleElement.add(nums[end]);
                    subsetList.add(singleElement);
                    return subsetList;
                }
                List<List<Integer>> subsetListMinus1 = _subsets(nums, end - 1);
                for (List<Integer> subset : subsetListMinus1) {
                    List<Integer> copy = new ArrayList<>(List.copyOf(subset));
                    copy.add(nums[end]);
                    subsetList.add(copy);
                }
                subsetList.addAll(subsetListMinus1);
                return subsetList;
            }

            public List<List<Integer>> subsets(int[] nums) {
                int len = nums.length;
                if (len == 0) {
                    return new ArrayList<>();
                }
                return _subsets(nums, len - 1);
            }
        }
    }
}