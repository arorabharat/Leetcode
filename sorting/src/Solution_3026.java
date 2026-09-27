import java.util.*;

public class Solution_3026 {
    class Solution {

        public long maximumSubarraySum(int[] nums, int k) {
            int n = nums.length;
            if(n < 2) {
                throw new IllegalArgumentException("size can not be less than 2");
            }
            Map<Integer, List<Integer>> value2Index = new HashMap<>();
            for (int i = 0; i < n; i++) {
                value2Index.computeIfAbsent(nums[i], _ -> new ArrayList<>());
                value2Index.get(nums[i]).add(i);
            }
            int[] prefixSum = new int[n+1];
            int currSum = 0;
            for (int i = 0; i < n; i++) {
                currSum = currSum + nums[i];
                prefixSum[i+1] = currSum;
            }
            int largestSum = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                List<Integer> indices1 = value2Index.get(nums[i] - k);
                List<Integer> indices2 = value2Index.get(nums[i] + k);
                if(indices1 != null) {
                    for(int j : indices1) {
                        int max = Math.max(j, i);
                        int min = Math.max(j, i);
                        int sum = prefixSum[max+1] - prefixSum[min];
                        largestSum = Math.max(largestSum, sum);
                    }
                }
                if(indices2 != null) {
                    for(int j : indices2) {
                        int max = Math.max(j, i);
                        int min = Math.max(j, i);
                        int sum = prefixSum[max+1] - prefixSum[min];
                        largestSum = Math.max(largestSum, sum);
                    }
                }
            }
            return largestSum == Integer.MIN_VALUE ? 0 : largestSum;
        }
    }
}
