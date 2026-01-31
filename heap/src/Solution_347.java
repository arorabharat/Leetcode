import java.util.*;

/**
 * https://leetcode.com/problems/top-k-frequent-elements/
 */
class Solution_347 {


    class Solution1 {

        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int num : nums) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
            int n = map.size();
            int[][] freq = new int[n][2];
            int i = 0;
            for (int num : map.keySet()) {
                freq[i][0] = num;
                freq[i][1] = map.get(num);
                i++;
            }
            PriorityQueue<int[]> maxHeap = new PriorityQueue<>(Comparator.comparingInt(a -> -a[1]));
            for (int j = 0; j < n; j++) {
                maxHeap.add(freq[j]);
            }
            int[] ans = new int[k];
            for (int j = 0; j < k; j++) {
                int[] front = maxHeap.remove();
                ans[j] = front[0];
            }
            return ans;
        }

    }

    class Solution2 {

        /**
         * Approach 2
         * Slight optimisation over approach 1
         *
         * @see Level#GOOD
         */
        public int[] topKFrequent2(int[] nums, int k) {
            // O(1) time
            if (k == nums.length) {
                return nums;
            }

            // 1. build hash map : character and how often it appears
            // O(N) time
            Map<Integer, Integer> count = new HashMap<>();
            for (int n : nums) {
                count.put(n, count.getOrDefault(n, 0) + 1);
            }

            // init minHeap 'the less frequent element first'
            Queue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(count::get));

            // 2. keep k top frequent elements in the minHeap
            // O(N log k) < O(N log N) time
            for (int n : count.keySet()) {
                minHeap.add(n);
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }

            // 3. build an output array
            // O(k log k) time
            int[] top = new int[k];
            for (int i = k - 1; i >= 0; --i) {
                top[i] = minHeap.poll();
            }
            return top;
        }


    }

    class Solution3 {

        Map<Integer, Integer> count = new HashMap<>();

        void swap(int[] nums, int i, int j) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }

        int partition(int[] nums, int s, int e) {
            int tr = s;
            int p = s;
            while (tr < e) {
                if (count.get(nums[tr]) < count.get(nums[e])) {
                    swap(nums, tr, p);
                    p++;
                }
                tr++;
            }
            swap(nums, p, e);
            return p;
        }

        void quickSort(int[] nums, int s, int e, int index) {
            if (s < e) {
                int p = partition(nums, s, e);
                if (p == index) {
                    return;
                }
                quickSort(nums, s, p - 1, index);
                quickSort(nums, p + 1, e, index);
            }
        }

        /**
         * Approach 3
         *
         * @see DSA#QUICK_SORT
         * Using quick sort
         */
        public int[] topKFrequent(int[] nums, int k) {
            if (k >= nums.length) {
                return nums;
            }
            for (int n : nums) {
                count.put(n, count.getOrDefault(n, 0) + 1);
            }
            int[] dist = count.keySet().stream().mapToInt(a -> a).toArray();
            quickSort(dist, 0, dist.length - 1, dist.length - k);
            int[] top = new int[k];
            int n = dist.length;
            for (int i = 0; i < k; i++) {
                top[i] = dist[n - i - 1];
            }
            return top;
        }
    }


    class Solution4 {


        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> freqMap = new HashMap<>();
            PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            for (int num : nums) {
                freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
            }
            for (int num : freqMap.keySet()) {
                q.add(new int[]{num, freqMap.get(num)});
                if (q.size() > k) {
                    q.remove();
                }
            }
            return q.stream().mapToInt(a -> a[0]).toArray();
        }

    }


}