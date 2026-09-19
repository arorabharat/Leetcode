package com.atlassian.dsa.sumk;

public class Solution_560 {

    class Solution {


        public int subarraySum(int[] nums, int k) {

            int length = nums.length;
            int[] prefixSum = new int[length + 1];
            for (int i = 0; i < length; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
            int totalSeq = 0;

            for (int i = 0; i < length; i++) {
                for (int j = i; j < length; j++) {
                    int sum = prefixSum[j + 1] - prefixSum[i];
                    if (sum == k) {
                        totalSeq++;
                    }
                }
            }
            return totalSeq;
        }
    }
}
