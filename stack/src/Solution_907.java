class Solution_907 {

    /**
     * Approach 1  : Brute force
     */
    public int sumSubarrayMins(int[] arr) {
        int ans = 0;
        int MODULO = (int) Math.pow(10, 9) + 7;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int min = arr[i];
            ans = (ans + arr[i]) % MODULO;
            for (int j = i + 1; j < n; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                }
                ans = (ans + min) % MODULO;
            }
        }
        return ans;
    }

}