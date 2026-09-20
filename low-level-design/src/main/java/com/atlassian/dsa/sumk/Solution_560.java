package com.atlassian.dsa.sumk;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

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

        class Solution2 {

            public int subarraySum(int[] nums, int k) {

                int length = nums.length;
                Map<Integer, TreeSet<Integer>> ps2Index = new HashMap<>();
                ps2Index.put(0, new TreeSet<>());
                ps2Index.get(0).add(0);
                int prefixSum = 0;
                for (int i = 0; i < length; i++) {
                    prefixSum = prefixSum + nums[i];
                    ps2Index.computeIfAbsent(prefixSum, x -> new TreeSet<>());
                    ps2Index.get(prefixSum).add(i + 1);
                }
                prefixSum = 0;
                int totalSeq = 0;
                for (int i = 0; i < length; i++) {
                    TreeSet<Integer> indices = ps2Index.get(k + prefixSum);
                    if (indices != null) {
                        totalSeq += indices.tailSet(i, false).size();
                    }
                    prefixSum = prefixSum + nums[i];
                }
                return totalSeq;
            }
        }
    }
}
