import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/distinct-numbers-in-each-subarray/
 */
class Solution_1852 {

    public int[] distinctNumbers(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < k && i < n; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        int i = 0;
        int j = i + k - 1;
        List<Integer> ans = new ArrayList<>();
        while (j < n - 1) {
            ans.add(map.size());
            int count = map.get(nums[i]);
            count--;
            if (count > 0) {
                map.put(nums[i], count);
            } else {
                map.remove(nums[i]);
            }
            i++;
            j++;
            map.put(nums[j], map.getOrDefault(nums[j], 0) + 1);
        }
        ans.add(map.size());
        int nk = ans.size();
        int[] ansArr = new int[nk];
        for (int l = 0; l < nk; l++) {
            ansArr[l] = ans.get(l);
        }
        return ansArr;
    }
}
