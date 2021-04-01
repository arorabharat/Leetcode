class Solution_134 {

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int reserve = 0;
        int n = gas.length;
        int totalGas = 0;
        int totalCost = 0;
        for (int i = 0; i < n; i++) {
            totalCost += cost[i];
            totalGas += gas[i];
        }
        if (totalCost > totalGas) {
            return -1;
        }
        int index = 0;
        for (int i = 0; i < n; i++) {
            reserve += (gas[i] - cost[i]);
            if (reserve < 0) {
                reserve = 0;
                index = i + 1;
            }
        }
        return index;
    }
}
