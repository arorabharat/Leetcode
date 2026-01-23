package com.atlassian.dsa.kclosest;

import java.util.*;

public class KClosest1 {

    public static void main(String[] args) {
        int[] arr = new int[]{1,1,1,10,10,10};
        new KClosest1().findClosestElements(arr, 1, 9);
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // Solve v1
        return findV3(arr, k, x);
    }

    public List<Integer> findV1(int[] arr, int k, int x) {
        /**
         1. Store distance in a multimap
         2. Store top k from multimap and sort them
         */

        Map<Integer, List<Integer>> distMap = new TreeMap<>();
        for (int num : arr) {
            int rank = Math.abs(num - x);
            distMap.computeIfAbsent(rank, k1 -> new ArrayList<>()).add(num);
        }

        // Collect the elements into an array till we run out of k
        List<Integer> result = new ArrayList<>();
        List<Integer> distances = new ArrayList<>(distMap.keySet());

        for (int distance : distances) {
            List<Integer> numbers = distMap.get(distance);
            for (int num : numbers) {
                if (result.size() >= k) break;
                result.add(num);
            }
            if (result.size() >= k) break;
        }
        Collections.sort(result);
        return result;
    }

    public List<Integer> findV2(int[] arr, int k, int x) {
        /**
         1. Get the l and r index in two pointers. Shrink from l and r based on distance.
         2. Then return the output from l to r
         */
        int l = 0, r = arr.length - 1;
        while (r - l >= k){
            if(Math.abs(arr[r] - x) >= Math.abs(arr[l] - x)) {
                r--;
            } else {
                l++;
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            ans.add(arr[i]);
        }
        System.out.println(ans);
        return ans;
    }

    public List<Integer> findV3(int[] arr, int k, int x) {
        /**
         1. Get the insertion pointer.
         2. Get the l and r pointer as adjacent to the insertion pointer.
         3. Collect the sum.
         */
        int insertionPoint = binarySearchSortedArrayInsertionPoint(arr, x);
        int left = insertionPoint - 1;
        int right = insertionPoint;

        List<Integer> result = new ArrayList<>();
        while (k > 0) {
            k--;
            if (left < 0) {
                result.add(arr[right++]);
            } else if (right >= arr.length) {
                result.add(arr[left--]);
            } else {
                int leftDist = Math.abs(x - arr[left]);
                int rightDist = Math.abs(x - arr[right]);
                if (leftDist <= rightDist) {
                    result.add(arr[left--]);
                } else {
                    result.add(arr[right++]);
                }
            }
        }
        Collections.sort(result);
        return result;
    }

    private int binarySearchSortedArrayInsertionPoint(int[] arr, int search) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left)/2;
            if (arr[mid] < search) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

}
