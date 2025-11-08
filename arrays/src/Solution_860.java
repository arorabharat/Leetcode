import java.util.HashMap;
import java.util.Map;

public class Solution_860 {

    public static final int LEMON_COST = 5;

    public boolean lemonadeChange(int[] bills) {
        Map<Integer, Integer> billCount = new HashMap<>();
        billCount.put(5, 0);
        billCount.put(10, 0);
        billCount.put(20, 0);
        int[] returnBillOrder = {20, 10, 5};
        for (int billReceived : bills) {
            int remainingBalance = billReceived - LEMON_COST;
            for (int returnBill : returnBillOrder) {
                if (remainingBalance == 0) {
                    break;
                }
                int expectedReturnBillCount = remainingBalance / returnBill;
                Integer currentReturnBillCount = billCount.get(returnBill);
                if (currentReturnBillCount >= expectedReturnBillCount) {
                    remainingBalance = remainingBalance - expectedReturnBillCount * returnBill;
                    billCount.put(returnBill, currentReturnBillCount - expectedReturnBillCount);
                } else {
                    remainingBalance = remainingBalance - currentReturnBillCount * returnBill;
                    billCount.put(expectedReturnBillCount, 0);
                }
            }
            if (remainingBalance > 0) {
                return false;
            }
            billCount.put(billReceived, billCount.get(billReceived) + 1);
        }
        return true;
    }
}
