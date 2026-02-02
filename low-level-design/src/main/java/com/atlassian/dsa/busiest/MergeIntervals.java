package com.atlassian.dsa.busiest;

import java.util.*;

// TODO

public class MergeIntervals {

     static class Pair {
        int s;
        int e;

         public int getS() {
             return s;
         }

         public int getE() {
             return e;
         }

         public Pair(int s, int e) {
             this.s = s;
             this.e = e;
         }

         @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return s == pair.s && e == pair.e;
        }

        @Override
        public int hashCode() {
            return Objects.hash(s, e);
        }
    }
    public List<int[]> mergeIntervals(List<int[]> windows) {
        int n = windows.size();
        if (n < 2) {
            return windows;
        }
        windows.sort(Comparator.comparingInt(a -> a[0])); // TC: O(N)
        //   ---------
        //      ----
        //       --------
        //          --------
        //

        //   ---------
        //       --------
        //          --------
        //
        int start = windows.get(0)[0];
        int end = windows.get(0)[1];
        PriorityQueue<Pair> windowFrequency = new PriorityQueue<>(Comparator.comparingInt(Pair::getE).reversed());
        windowFrequency.add(new Pair(start, end));
        for (int i = 1; i < n; i++) {
            int s = windows.get(i)[0];
            int e = windows.get(i)[1];
            int ls = windowFrequency.peek().getS();
            int le = windowFrequency.peek().getE();
            if(e < le) {
            } else if (e == le) {

            }  else {

            }

        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {

    }
}
