import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/product-of-the-last-k-numbers/
 */
class Solution_1352 {

    static class ProductOfNumbers {

        List<Integer> productListSinceLastZero;
        int productSinceLastZero;

        public ProductOfNumbers() {
            productListSinceLastZero = new ArrayList<>();
            productListSinceLastZero.add(1);
            productSinceLastZero = 1;
        }

        public void add(int num) {
            if (num == 0) {
                productListSinceLastZero = new ArrayList<>();
                productListSinceLastZero.add(1);
                productSinceLastZero = 1;
            } else {
                productSinceLastZero *= num;
                productListSinceLastZero.add(productSinceLastZero);
            }
        }

        public int getProduct(int k) {
            if (k < 1 || productListSinceLastZero.size() <= k) {
                return 0;
            }
            int n = productListSinceLastZero.size();
            return productSinceLastZero / productListSinceLastZero.get(n - k - 1);
        }
    }
}
