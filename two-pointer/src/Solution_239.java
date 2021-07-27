import java.util.TreeMap;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 */
class Solution_239 {

    static class FreqManager extends TreeMap<Integer, Integer> {

        public void decFreq(int element) {
            int count = this.getOrDefault(element, 0);
            count--;
            if (count > 0) {
                this.put(element, count);
            } else {
                this.remove(element);
            }
        }

        public void incFreq(int element) {
            this.put(element, this.getOrDefault(element, 0) + 1);
        }

        public Integer getHighestKey() {
            return this.lastKey();
        }
    }

    /**
     * Store the frequency of the each element in the k size window.
     * Use tree map which keeps the value in the sorted order.
     * Keep updating the frequency f the element as we move the window. Last key will be the largest key int the map.
     */
    public int[] maxSlidingWindow(int[] nums, int k) {

        FreqManager freqManager = new FreqManager();
        int n = nums.length;
        if (n == 0) return new int[0];
        int res_n = n - k + 1;
        int res_i = 0;
        int[] result = new int[res_n];
        for (int i = 0; i < k; i++) {
            freqManager.incFreq(nums[i]);
        }
        result[res_i] = freqManager.getHighestKey();
        res_i++;
        for (int j = k; j < n; j++) {
            freqManager.decFreq(nums[j - k]);
            freqManager.incFreq(nums[j]);
            result[res_i] = freqManager.getHighestKey();
            res_i++;
        }
        return result;
    }
}