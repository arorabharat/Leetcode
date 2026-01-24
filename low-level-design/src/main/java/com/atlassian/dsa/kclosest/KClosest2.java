package com.atlassian.dsa.kclosest;

import java.util.*;
import java.util.stream.Stream;

public class KClosest2 {

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> srotedList = new ArrayList<>();
        for (int a : arr) {
            srotedList.add(a);
        }
        Comparator<Integer> sortByCloseness = (a1, a2) -> {
            if (Math.abs(a1 - x) == Math.abs(a2 - x)) {
                return Integer.compare(a1, a2);
            } else {
                return Math.abs(a1 - x) - Math.abs(a2 - x);
            }
        };
        srotedList.sort(sortByCloseness);
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            results.add(srotedList.get(i));
        }
        Collections.sort(results);
        return results;
    }

    public List<Integer> findClosestElements2(int[] arr, int k, int x) {

        Comparator<Integer> sortByCloseness = (a1, a2) -> {
            if (Math.abs(a1 - x) == Math.abs(a2 - x)) {
                return Integer.compare(a1, a2);
            } else {
                return Math.abs(a1 - x) - Math.abs(a2 - x);
            }
        };

        PriorityQueue<Integer> q = new PriorityQueue<>(sortByCloseness.reversed());

        for (int a : arr) {
            q.add(a);
            if (q.size() > k) {
                q.poll();
            }
        }
        Stream<Integer> stream = q.stream();
        return stream.sorted().toList();
    }

    // a1,  x  an, x
    public List<Integer> findClosestElements3(int[] arr, int k, int x) {
        int n = arr.length;
        int s = 0;
        int e = n - 1;
        // 0 4 5
        while (s < e) {
            int m = (e + s) / 2;
            if (arr[m] >= x) {
                s = m;
            } else {
                e = m - 1;
            }
        }
        s = s - 1;
        e = s + 1;
        while (e - s - 1 < k) {
            if (s == -1) {
                e++;
            } else if (e == arr.length) {
                s--;
            } else {
                if (Math.abs(arr[e] - x) > Math.abs(arr[s] - x)) {
                    s--;
                } else if (Math.abs(arr[e] - x) < Math.abs(arr[s] - x)) {
                    e++;
                } else {
                    s--;
                }
            }
        }
        List<Integer> results = new ArrayList<>();
        for (int i = s + 1; i < e; i++) {
            results.add(arr[s]);
        }
        return results;
    }

    public static void main(String[] args) {

    }
}
