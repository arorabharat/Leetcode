import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/two-sum/
 * 
 * Problem: Given an array of integers nums and an integer target, 
 * return indices of the two numbers such that they add up to target.
 * 
 * Time Complexity Analysis:
 * - HashMap approach: O(n) time, O(n) space - OPTIMAL
 * - Two pointer with sort: O(n log n) time, O(n) space
 * - Binary search: O(n log n) time, O(n) space
 * - Brute force: O(n²) time, O(1) space
 *
 * @see Solution_15
 */
class Solution_1 {

    /**
     * Approach 1: Two Pointer with Sorting (using Record)
     * Time: O(n log n), Space: O(n)
     * Optimized: Using Integer.compare to avoid overflow, cleaner comparator
     */
    class Approach_1 {
        record Pair(int value, int index) {
        }

        public int[] twoSum(int[] nums, int target) {
            int n = nums.length;
            if (n < 2) {
                return new int[]{-1, -1};
            }
            Pair[] pairs = new Pair[n];
            for (int i = 0; i < n; i++) {
                pairs[i] = new Pair(nums[i], i);
            }
            // Using Integer.compare to avoid integer overflow
            Arrays.sort(pairs, Comparator.comparingInt(Pair::value));
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int sum = pairs[left].value() + pairs[right].value();
                if (sum == target) {
                    return new int[]{pairs[left].index(), pairs[right].index()};
                } else if (sum > target) {
                    right--;
                } else {
                    left++;
                }
            }
            return new int[]{-1, -1};
        }
    }

    /**
     * Approach 2: HashMap (One-Pass) - OPTIMAL
     * Time: O(n), Space: O(n)
     * 
     * We use a running hash to avoid picking the same number twice.
     * For each element, check if (target - current) exists in map.
     * If found, return indices; otherwise, add current to map.
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    /**
     * Approach 3: Two Pointer with Sorting (using Record - optimized version of twoSum2)
     * Time: O(n log n), Space: O(n)
     * Optimized: Using record instead of 2D array for better readability and performance
     */
    public int[] twoSum2(int[] nums, int target) {
        int n = nums.length;
        record Pair(int value, int index) {
        }
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(nums[i], i);
        }
        Arrays.sort(pairs, Comparator.comparingInt(Pair::value));
        int left = 0, right = n - 1;
        while (left < right) {
            int sum = pairs[left].value() + pairs[right].value();
            if (sum == target) {
                return new int[]{pairs[left].index(), pairs[right].index()};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * Approach 4: Brute Force (Optimized)
     * Time: O(n²), Space: O(1)
     * Optimized: Start j from i+1 to avoid checking same pairs twice and remove redundant i != j check
     */
    public int[] twoSum3(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * Approach 5: Binary Search (Fixed and Optimized)
     * Time: O(n log n), Space: O(n)
     * Fixed: Properly sorts array with indices and searches on sorted array
     */
    private int binarySearch(Pair[] arr, int start, int end, int key) {
        while (start <= end) {
            int mid = start + (end - start) / 2; // Avoid overflow
            if (arr[mid].value() == key) {
                return mid;
            } else if (arr[mid].value() > key) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    record Pair(int value, int index) {
    }

    public int[] twoSum4(int[] nums, int target) {
        int n = nums.length;
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(nums[i], i);
        }
        Arrays.sort(pairs, Comparator.comparingInt(Pair::value));
        for (int i = 0; i < n; i++) {
            int complement = target - pairs[i].value();
            int j = binarySearch(pairs, i + 1, n - 1, complement);
            if (j != -1) {
                return new int[]{pairs[i].index(), pairs[j].index()};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * Approach 6: TreeMap (Different Data Structure)
     * Time: O(n log n), Space: O(n)
     * Uses TreeMap instead of HashMap - maintains sorted order but slower
     * Note: Less efficient than HashMap but demonstrates different approach
     */
    public int[] twoSum5(int[] nums, int target) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    /**
     * Approach 7: Stream-based (Functional Programming Style)
     * Time: O(n²), Space: O(1)
     * Different approach using Java Streams - more readable but less efficient
     * Note: This is more of a demonstration of functional style, not optimal
     */
    public int[] twoSum6(int[] nums, int target) {
        return IntStream.range(0, nums.length)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, nums.length)
                        .filter(j -> nums[i] + nums[j] == target)
                        .mapToObj(j -> new int[]{i, j}))
                .findFirst()
                .orElse(new int[]{-1, -1});
    }

    /**
     * Approach 8: HashMap with Early Size Optimization
     * Time: O(n), Space: O(n)
     * Optimized: Pre-allocates HashMap with expected size to reduce rehashing
     */
    public int[] twoSum7(int[] nums, int target) {
        // Pre-allocate with load factor to reduce rehashing
        Map<Integer, Integer> map = new HashMap<>((int) (nums.length / 0.75f) + 1);
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            Integer complementIndex = map.get(complement);
            if (complementIndex != null) {
                return new int[]{complementIndex, i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

}
