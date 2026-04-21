import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution_1058 {

    class Solution {

        public static final int MAX_DECIMAL_SCALE = 1000;

        int parseScaledPrice(String price) {
            String[] parts = price.split("\\.");
            int beforeDecimal = Integer.parseInt(parts[0]);
            int afterDecimal = Integer.parseInt(parts[1]);
            return beforeDecimal * MAX_DECIMAL_SCALE + afterDecimal;
        }

        public String minimizeError(String[] prices, int target) {

            int n = prices.length;
            int sum = 0;
            int floorSum = 0;
            int scaledTarget = target * 1000;
            List<Integer> scaledPrices = new ArrayList<>();
            int count = 0;
            for (String price : prices) {
                int scaledPrice = parseScaledPrice(price);
                scaledPrices.add(scaledPrice);
                sum = sum + scaledPrice;
                floorSum = floorSum + (scaledPrice / 1000);
                if (scaledPrice % 1000 != 0) {
                    count++;
                }
            }
            if (target > (count + floorSum) || target < floorSum) {
                return "-1";
            } else {
                int error = 0;
                scaledPrices.sort(Comparator.comparingInt(a -> -(a % 1000)));
                int diff = target - floorSum;
                for (int i = 0; i < n; i++) {
                    Integer scaledPrice = scaledPrices.get(i);
                    if (i < diff) {
                        error = error + (1000 - (scaledPrice % 1000));
                    } else {
                        error = error + (scaledPrice % 1000);
                    }
                }
                String errorStr = String.valueOf(error);
                int digits = errorStr.length();
                if (digits == 1) {
                    return "0.00" + error;
                } else if (digits == 2) {
                    return "0.0" + error;
                }
                return error / 1000 + "." + errorStr.substring(digits - 3);
            }
        }
    }
}
