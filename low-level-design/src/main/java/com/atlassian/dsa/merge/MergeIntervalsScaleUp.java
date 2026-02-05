package com.atlassian.dsa.merge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervalsScaleUp {


    // [{2,5}, {4,8}]

    // Case - 1
    // --------------------
    //      ----------------
    //           -----
    //                -----


    //        ----------------
    //     --------

    // Case - 2
    // ------------
    //     ------
    //     ------

    // Case - 3
    // ------------
    //     --------
    //     --------


    // Case - 4
    // ------------
    //                    ------------
//    public static List<int[]> minTwoWindow(List<int[]> windows) {
//
//        // base case
//        if (windows.size() < 2) {
//            return windows;
//        }
//
//        // Step - 1 Sort by start
//        Comparator<int[]> sortByStartTime = Comparator.comparingInt(w -> w[0]);
//        windows.sort(sortByStartTime);
//
//        // Merge with last element based on the above cases
//        List<int[]> atLeastTwo = new ArrayList<>();
//
//        for (int i = 1; i < windows.size(); i++) {
//
//            int[] prevWindow = windows.get(i-1);
//            int[] nextWindow = windows.get(i);
//
//            int ns = nextWindow[0];
//            int ne = nextWindow[1];
//            int ps = nextWindow[1];
//
//            if (nextWindow[0] < prevWindow[1]) {
//                atLeastTwo.add(new int[]{Math.max(ps, ns), Math.min(pe, ne)});
//            }
//        }
//
//
//        for (int i = 1; i < windows.size(); i++) {
//
//            int[] nextWindow = windows.get(i);
//
//            int ns = nextWindow[0];
//            int ne = nextWindow[1];
//
//
//        }
//        return atLeastTwo;
//
//    }
//
//
//
//
//    public static void main(String[] args) {
//        System.out.println("=============================");
//        List<int[]> windows1 = new ArrayList<>();
//        windows1.add(new int[]{2,5});
//        windows1.add(new int[]{4,8});
//        List<int[]> result1 = minOneWindow(windows1);
//        for (int[] window : result1) {
//            System.out.println(Arrays.toString(window));
//        }
//
//        System.out.println("==============================");
//        List<int[]> windows2 = new ArrayList<>();
//        windows2.add(new int[]{2,5});
//        windows2.add(new int[]{4,8});
//        windows2.add(new int[]{12,15});
//        windows2.add(new int[]{13,17});
//        windows2.add(new int[]{13,18});
//        List<int[]> result2 = minOneWindow(windows2);
//        for (int[] window : result2) {
//            System.out.println(Arrays.toString(window));
//        }
//    }
}
